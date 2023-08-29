package tech.xavi.wschat.service.spamfilter;

import tech.xavi.wschat.configuration.ChatConfiguration;
import tech.xavi.wschat.entity.sub.ChatMessage;
import tech.xavi.wschat.entity.sub.SpamType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpamFilterChain {

    private int maxSpamPoints;
    private final List<SpamFilter> filters;
    private final Map<String,Integer> spamPointsControl;
    private SpamFilterChain(Builder builder) {
        maxSpamPoints = ChatConfiguration.getInstance().INITIAL_MAX_SPAM_POINTS;;
        spamPointsControl = new HashMap<>();
        this.filters = builder.filters;
    }
    public SpamType filter(ChatMessage chatPayload) {
        final int userSpamPoints = spamPointsControl.get(chatPayload.getUserId());
        if (userSpamPoints >= maxSpamPoints) return SpamType.BANNED_USER;
        for (SpamFilter filter : filters) {
            final SpamType spamType = filter.checkSpam(chatPayload);
            if (spamType != SpamType.CLEAN) {
                spamPointsControl.put(
                        chatPayload.getUserId(),
                        spamPointsControl.get(chatPayload.getUserId()) + 1
                );
                return spamType;
            }
        }
        return SpamType.CLEAN;
    }

    public void setNewUserAndFilter(String userId){
        spamPointsControl.put(userId,0);
    }

    public static class Builder {
        private final List<SpamFilter> filters;
        public Builder() {
            filters = new ArrayList<>();
        }

        public Builder addFilter(SpamFilter filter) {
            filters.add(filter);
            return this;
        }

        public SpamFilterChain build() {
            return new SpamFilterChain(this);
        }
    }
}






