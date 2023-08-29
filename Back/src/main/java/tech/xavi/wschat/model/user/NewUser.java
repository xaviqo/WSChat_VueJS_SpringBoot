package tech.xavi.wschat.model.user;

import lombok.Builder;
import tech.xavi.wschat.entity.sub.Avatar;

@Builder
public record NewUser(
        String nickname,
        String password,
        Avatar avatar
) {
}
