package tech.xavi.wschat.model.setup;

public record CreateRoomReq(
        String topic,
        String nickname,
        String avatarStyle,
        int roomCapacity
) {
}
