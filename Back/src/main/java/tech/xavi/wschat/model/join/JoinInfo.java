package tech.xavi.wschat.model.join;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record JoinInfo(
        String roomId,
        int maxCapacity,
        int subscribedUsers,
        String roomName,
        LocalDateTime creationDate,
        boolean available

) {
}
