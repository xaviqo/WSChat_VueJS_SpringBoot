package tech.xavi.wschat.controller.webstocket;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import tech.xavi.wschat.entity.sub.ChatMessage;
import tech.xavi.wschat.entity.sub.MessageType;
import tech.xavi.wschat.service.websocket.ActivityMessageServiceWS;
import tech.xavi.wschat.service.websocket.ConnectionServiceWS;

@Slf4j
@RequiredArgsConstructor
@Controller
public class MessageControllerWS {

    private final ConnectionServiceWS wsConnectionService;
    private final ActivityMessageServiceWS wsMessageService;

    @MessageMapping("/chat/{roomId}")
    public void chatRoomMessageEndpoint(@DestinationVariable String roomId, ChatMessage message) {
        log.info(message.getMessage());
        final boolean roomIdsAreEqual = message.getRoomId().equals(roomId);
        if (roomIdsAreEqual && wsConnectionService.isUserWSConnected(message.getUserId(), message.getRoomId())) {
            message.setMessageType(MessageType.MESSAGE);
            wsMessageService.prepareMessage(message);
        }
    }

}
