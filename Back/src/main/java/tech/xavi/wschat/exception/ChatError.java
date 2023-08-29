package tech.xavi.wschat.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import tech.xavi.wschat.configuration.ChatConfiguration;

@Getter
@AllArgsConstructor
public enum ChatError {

    FatalError("099","Fatal error ocurred. Contact the administrator"),
    // ROOM ERROR
    TopicOnlyAZ09("100","Room names can only contain letters or numbers"),
    MinRoomSizeExceeded("101","The minimum room size is 2 users"),
    MaxRoomSizeExceeded("102","The maximum room size is "+ ChatConfiguration.getInstance().MAX_ROOM_CAPACITY),
    RoomNotValid("103","You are not authenticated in this room"),
    RoomIdNotFound("104","The specified room ID does not exist"),
    AlreadyInChatRoom("105","You are already in this chat room"),
    RoomIsFull("106","The chat room is full, try again later"),
    InvalidRoomPassword("107","Incorrect password, please try again"),
    // USER ERROR
    UserIdNotFound("200","User ID not found"),
    NicknameOnlyAZ09("201","Nicknames can only contain letters or numbers"),
    NicknameMaxLength("202","Nicknames maximum length is "+ ChatConfiguration.getInstance().MAX_NICK_LENGTH+" characters"),
    NicknameMinLength("203","Nicknames minimum length is "+ ChatConfiguration.getInstance().MIN_NICK_LENGTH+" characters"),
    InvalidAvatar("204","The specified avatar is not available")
    // AUTH ERROR

    ;

    private final String code;
    private final String message;

}
