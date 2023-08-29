package tech.xavi.wschat.entity.sub;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChatMessage {
    private String roomId;
    private String userId;
    private String senderNickname;
    private String destination;
    private LocalDateTime timeStamp;
    private MessageType messageType;
    private SpamType spamType;
    private String message;
}
