package tech.xavi.wschat.service.websocket;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.xavi.wschat.entity.sub.ChatMessage;
import tech.xavi.wschat.entity.sub.MessageType;
import tech.xavi.wschat.entity.sub.SpamType;
import tech.xavi.wschat.service.spamfilter.SpamFilterChain;

import java.util.Map;

@RequiredArgsConstructor
@Service
public class SpamServiceWS {

    private final Map<String, SpamFilterChain> spamFiltersByRoomId;
    private final SpamFilterChain spamFilterChain;

    public SpamType filterSpam(ChatMessage chatPayload) {
        SpamFilterChain spamFilterChain = spamFiltersByRoomId.get(chatPayload.getRoomId());
        return spamFilterChain.filter(chatPayload);
    }

    public void addNewUser(String userId, String roomId){
        if (!spamFiltersByRoomId.containsKey(roomId)) addNewSpamFilter(roomId);
        final SpamFilterChain spamFilterChain = spamFiltersByRoomId.get(roomId);
        spamFilterChain.setNewUserAndFilter(userId);
    }

    private void addNewSpamFilter(String roomId){
        spamFiltersByRoomId.put(
                roomId,
                spamFilterChain
        );
    }

    public String prepareMessage(ChatMessage chatMessage){
        if (isSpamOrBanned(chatMessage)){
            chatMessage.setMessage(SpamType.getMessage(chatMessage));
        }
        return chatMessage.getMessage();
    }

    private boolean isSpamOrBanned(ChatMessage chatMessage){
        return chatMessage.getMessageType() == MessageType.SPAM
                || chatMessage.getMessageType() == MessageType.BAN;
    }

    // TODO: Cuando se añade a remove, esperar 10min
    public void removeSpamFilter(String roomId){
        spamFiltersByRoomId.remove(roomId);
    }
}
