package tech.xavi.wschat.model.user;

import lombok.Builder;
import tech.xavi.wschat.entity.sub.Avatar;

@Builder
public record RoomUser(
        String id,
        String nickname,
        String avatar,
        boolean connected
) {
}
