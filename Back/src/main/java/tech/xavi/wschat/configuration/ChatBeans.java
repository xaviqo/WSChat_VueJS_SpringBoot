package tech.xavi.wschat.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import tech.xavi.wschat.service.spamfilter.*;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ChatBeans {

    @Bean
    public ChatConfiguration chatCfg() {
        return ChatConfiguration.getInstance();
    }

    @Bean
    public SpamFilterChain spamFilterChain(){
        return new SpamFilterChain.Builder()
                .addFilter(new MessageLengthSpamFilter())
                .addFilter(new TimeBetweenMessagesSpamFilter())
                .addFilter(new MessageContainLinkSpamFilter())
                .addFilter(new MessageRepeatSpamFilter())
                .addFilter(new BannedWordsSpamFilter())
                .build();
    }

    @Bean
    @Scope("singleton")
    public Map<String, SpamFilterChain> spamFiltersByRoomId() {
        return new HashMap<>();
    }

}
