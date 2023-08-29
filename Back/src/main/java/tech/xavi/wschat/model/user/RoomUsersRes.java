package tech.xavi.wschat.model.user;

import lombok.Builder;

import java.util.Set;

@Builder
public record RoomUsersRes(
        Set<RoomUser> inscribedUsers
) {
}
