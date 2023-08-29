package tech.xavi.wschat.service.spamfilter;


import tech.xavi.wschat.configuration.ChatConfiguration;
import tech.xavi.wschat.entity.sub.ChatMessage;
import tech.xavi.wschat.entity.sub.SpamType;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static java.time.temporal.ChronoUnit.SECONDS;

public class TimeBetweenMessagesSpamFilter implements SpamFilter{

    private int secBetweenMsg;
    private final Map<String, LocalDateTime> lastMessageTimesByUser;

    @Override
    public SpamType checkSpam(ChatMessage message) {
        final LocalDateTime lastMsgTime = lastMessageTimesByUser.get(message.getUserId());
        final long diff = SECONDS.between(
                lastMsgTime != null ? lastMsgTime : LocalDateTime.now().minusYears(1),
                message.getTimeStamp()
        );
        lastMessageTimesByUser.put(message.getUserId(),message.getTimeStamp());
        return (diff <= getSecBetweenMsg()) ? SpamType.TIME_BETWEEN_MSG : SpamType.CLEAN;
    }

    public TimeBetweenMessagesSpamFilter() {
        this.secBetweenMsg = ChatConfiguration.getInstance().SEC_BETWEEN_MSG_DEFAULT;
        this.lastMessageTimesByUser = new HashMap<>();
    }

    public int getSecBetweenMsg() {
        return secBetweenMsg;
    }

    public void setSecBetweenMsg(int secBetweenMsg) {
        this.secBetweenMsg = secBetweenMsg;
    }
}
