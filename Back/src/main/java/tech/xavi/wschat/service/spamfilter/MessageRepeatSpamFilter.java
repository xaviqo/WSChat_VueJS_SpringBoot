package tech.xavi.wschat.service.spamfilter;

import tech.xavi.wschat.entity.sub.ChatMessage;
import tech.xavi.wschat.entity.sub.SpamType;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MessageRepeatSpamFilter implements SpamFilter{

    private final Map<String,String> lastSentMessagesByUser;

    @Override
    public SpamType checkSpam(ChatMessage message) {
        if (!lastSentMessagesByUser.containsKey(message.getUserId())) {
            lastSentMessagesByUser.put(message.getUserId(), UUID.randomUUID().toString());
        }
        if (!message.getMessage().isBlank()){
            if (lastSentMessagesByUser.get(message.getUserId())
                    .equalsIgnoreCase(message.getMessage())){
                return SpamType.REPEATED_MSG;
            }
        }
        lastSentMessagesByUser.put(message.getUserId(),message.getMessage());
        return SpamType.CLEAN;
    }

    public MessageRepeatSpamFilter() {
        this.lastSentMessagesByUser = new HashMap<>();
    }
}
