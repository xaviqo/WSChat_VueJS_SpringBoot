package tech.xavi.wschat.entity;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import tech.xavi.wschat.entity.sub.Avatar;
import tech.xavi.wschat.entity.sub.Role;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatUser implements UserDetails {

    @Id
    @Column(length = 8)
    private String id;
    @Column(length = 24, nullable = false)
    private String username;
    @Column(length = 60, nullable = false)
    private String password;
    @Column(nullable = false)
    private Avatar avatar;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Set<ChatUserStatus> chatRooms;
    @OneToMany(mappedBy = "token", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<RefreshToken> refreshToken;

    public ChatUser(String id) {
        this.id = id;
    }

    public void setNewChatRoom(ChatRoom room){
        chatRooms.add(
          new ChatUserStatus(this.getId(),room.getId())
        );
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(Role.USER.name()));
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
