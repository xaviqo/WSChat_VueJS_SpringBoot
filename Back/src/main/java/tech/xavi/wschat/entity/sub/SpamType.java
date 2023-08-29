package tech.xavi.wschat.entity.sub;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SpamType {

    LIMIT("%s has exceeded the spam limit and can no longer send messages","Spam limit reached"),
    FFC_BANNED_WORD("%s is using banned words","Using FFC banned word"),
    ROOM_BANNED_WORD("%s is using banned words","Using room banned word"),
    MSG_TOO_SHORT("%s message does not meet the minimum length requirement","Message is too short"),
    MSG_TOO_LONG("%s has exceeded the limit of characters per message","Message size limit exceeded"),
    REPEATED_MSG("%s is repeating same message in the chat","Repeated message"),
    TIME_BETWEEN_MSG("%s should leave more time between chat messages","Not enough time between messages"),
    URL_SENT("%s is sending URLs","Sending URLs"),
    BANNED_USER("%s is banned from the chatroom","User is banned"),
    CLEAN("","")
    ;
    private final String message;
    private final String type;

    public static String getMessage(ChatMessage chatMessage) {
        for (SpamType spamType : values()){
            if (chatMessage.getSpamType().equals(spamType)) {
                return String.format(
                        spamType.getMessage(),
                        chatMessage.getSenderNickname()
                );
            }
        }
        return CLEAN.getMessage();
    }
}