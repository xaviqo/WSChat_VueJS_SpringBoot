package tech.xavi.wschat.model.ws;

import lombok.Builder;
import tech.xavi.wschat.entity.sub.MessageType;
import tech.xavi.wschat.entity.sub.SpamType;

import java.time.LocalDateTime;

@Builder
public record Activity(
        Long activityId,
        String userId,
        LocalDateTime sentDate,
        MessageType messageType,
        SpamType spamType,
        String message
) {
}
