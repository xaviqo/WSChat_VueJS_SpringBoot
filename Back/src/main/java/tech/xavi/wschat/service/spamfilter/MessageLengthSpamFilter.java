package tech.xavi.wschat.service.spamfilter;


import tech.xavi.wschat.configuration.ChatConfiguration;
import tech.xavi.wschat.entity.sub.ChatMessage;
import tech.xavi.wschat.entity.sub.SpamType;

public class MessageLengthSpamFilter implements SpamFilter{
    @Override
    public SpamType checkSpam(ChatMessage message) {
        if (message.getMessage().length() <= ChatConfiguration.getInstance().MIN_MSG_LENGTH)
            return SpamType.MSG_TOO_SHORT;
        if (message.getMessage().length() >= ChatConfiguration.getInstance().MAX_MSG_LENGTH)
            return SpamType.MSG_TOO_LONG;
        return SpamType.CLEAN;
    }
}
