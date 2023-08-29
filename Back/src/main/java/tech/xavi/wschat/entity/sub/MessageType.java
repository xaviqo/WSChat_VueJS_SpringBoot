package tech.xavi.wschat.entity.sub;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MessageType {
    MESSAGE(""),
    SPAM(" is sending spam"),
    JOIN(" connected to the room"),
    LEAVE(" left the room"),
    KICK(" kicked from the room"),
    BAN(" banned from the room"),
    DELETE("The room has been deleted");
    private final String message;

}
