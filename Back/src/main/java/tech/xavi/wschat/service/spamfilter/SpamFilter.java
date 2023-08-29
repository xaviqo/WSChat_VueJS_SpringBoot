package tech.xavi.wschat.service.spamfilter;

import tech.xavi.wschat.entity.sub.ChatMessage;
import tech.xavi.wschat.entity.sub.SpamType;

public interface SpamFilter {
    SpamType checkSpam(ChatMessage message);
}
