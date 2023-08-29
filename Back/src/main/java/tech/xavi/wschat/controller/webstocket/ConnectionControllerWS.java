package tech.xavi.wschat.controller.webstocket;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import tech.xavi.wschat.entity.ChatActivity;
import tech.xavi.wschat.entity.ChatUser;
import tech.xavi.wschat.entity.sub.MessageType;
import tech.xavi.wschat.entity.sub.SpamType;
import tech.xavi.wschat.service.room.ChatRoomStatusService;
import tech.xavi.wschat.service.user.JwtService;
import tech.xavi.wschat.service.websocket.ActivityMessageServiceWS;
import tech.xavi.wschat.service.websocket.ConnectionServiceWS;
import tech.xavi.wschat.service.websocket.SpamServiceWS;
import tech.xavi.wschat.utils.IDGenerator;

import java.time.LocalDateTime;

@Slf4j
@AllArgsConstructor
@Component
public class ConnectionControllerWS {

    private final ConnectionServiceWS connectionServiceWS;
    private final ActivityMessageServiceWS activityMessageServiceWS;
    private final ChatRoomStatusService chatRoomStatusService;
    private final JwtService jwtService;
    private final SpamServiceWS spamServiceWS;

    @EventListener
    public void handleConnect(SessionConnectEvent event) {
        final StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        final String userId = accessor.getNativeHeader("senderId").get(0);
        final String token = accessor.getNativeHeader("token").get(0);
        final String nickname = accessor.getNativeHeader("senderName").get(0);
        final String roomId = accessor.getNativeHeader("roomId").get(0);

        final boolean correctToken =
                jwtService.extractUserId(token).equals(userId)
                        && jwtService.extractUsername(token).equals(nickname)
                        && jwtService.extractRooms(token).contains(roomId);

        if (correctToken && chatRoomStatusService.isUserInscribedToChatRoom(userId, roomId)) {
            connectionServiceWS.switchUserStatus(userId, roomId, true);
            spamServiceWS.addNewUser(userId, roomId);

            activityMessageServiceWS.sendActivity(
                    ChatActivity.builder()
                            .activityId(IDGenerator.generateActivityId(LocalDateTime.now()))
                            .chatUser(new ChatUser(userId))
                            .messageType(MessageType.JOIN)
                            .spamType(SpamType.CLEAN)
                            .message(nickname+MessageType.JOIN)
                            .build(),
                    activityMessageServiceWS.getMessageDestination(roomId)
            );
        }

        log.info("Usuario "+nickname+" se conecta, resultado: "+correctToken);
    }

    @EventListener
    public void handleDisconnect(SessionDisconnectEvent event) {

        final String[] sessionId = event.getSessionId().split(":");
        final String roomId = sessionId[0];
        final String userId = sessionId[1];
        final String nickname = sessionId[2];

        connectionServiceWS.switchUserStatus(userId,roomId,false);

        activityMessageServiceWS.sendActivity(
                ChatActivity.builder()
                        .activityId(IDGenerator.generateActivityId(LocalDateTime.now()))
                        .chatUser(new ChatUser(userId))
                        .messageType(MessageType.LEAVE)
                        .spamType(SpamType.CLEAN)
                        .message(nickname+MessageType.LEAVE)
                        .build(),
                activityMessageServiceWS.getMessageDestination(roomId)
        );

        log.info("Usuario "+nickname+" se desconecta");
    }
}
