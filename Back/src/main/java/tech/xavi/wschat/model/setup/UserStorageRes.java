package tech.xavi.wschat.model.setup;

import lombok.Builder;
import tech.xavi.wschat.entity.sub.TokenPayload;

@Builder
public record UserStorageRes(
        String userId,
        String userNickname,
        String roomTopic,
        TokenPayload tokenPayload,
        String avatarStyle,
        String chatId
) {
}
