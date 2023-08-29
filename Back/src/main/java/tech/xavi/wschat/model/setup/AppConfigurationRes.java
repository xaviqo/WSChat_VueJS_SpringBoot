package tech.xavi.wschat.model.setup;

import lombok.Builder;

import java.util.Set;

@Builder
public record AppConfigurationRes(
        int maxRoomCapacity,
        Set<String> availableAvatars
) {
}
