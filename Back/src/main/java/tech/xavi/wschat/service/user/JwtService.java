package tech.xavi.wschat.service.user;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

@Service
public class JwtService {

    private final String SECRET_KEY;
    private final String ISSUER;

    public JwtService(
            @Value("${freefastchat.jwt.secret}") String secretKey,
            @Value("${freefastchat.jwt.issuer}") String issuer
    ) {
        this.SECRET_KEY = secretKey;
        this.ISSUER = issuer;
    }

    public boolean isTokenValid(String token, UserDetails userDetails){
        return extractUserId(token)
                .equals(userDetails.getUsername())
                && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token){
        return extractExpiration(token)
                .before(new Date());
    }

    private Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }


    public String generateAccessToken(
            String userName,
            String userId,
            List<String> roomIds
    ) {
        return Jwts.builder()
                .claim("userId",userId)
                .claim("roomIds",roomIds)
                .setIssuer(ISSUER)
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Collection<String> extractRooms(String token) {
        return extractClaim(token, claims -> claims.get("roomIds", Collection.class))
                .stream()
                .toList();
    }

/*    public Collection<? extends GrantedAuthority> extractRooms(String token) {
        return (Collection<? extends GrantedAuthority>)
                extractClaim(token, claims -> claims.get("roomIds", Collection.class))
                        .stream()
                        .map(str -> new SimpleGrantedAuthority((String) str))
                        .toList();
    }*/

    public String extractUserId(String token) {
        return extractClaim(token, claims -> claims.get("userId", String.class));
    }

    public String extractUsername(String token){
        return extractClaim(token,Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolver){
        return claimResolver.apply(
                extractAllClaims(token)
        );
    }

    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        return Keys.hmacShaKeyFor(
                Decoders.BASE64.decode(SECRET_KEY)
        );
    }
}
