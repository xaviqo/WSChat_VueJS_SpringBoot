package tech.xavi.wschat.entity.sub;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenPayload {
    String accessToken;
    String refreshToken;
}