package tech.xavi.wschat.service.spamfilter;


import tech.xavi.wschat.configuration.ChatConfiguration;
import tech.xavi.wschat.entity.sub.ChatMessage;
import tech.xavi.wschat.entity.sub.SpamType;

public class MessageContainLinkSpamFilter implements SpamFilter{
    private boolean allowUrls;
    @Override
    public SpamType checkSpam(ChatMessage message) {
        if (isAllowUrls()) return SpamType.CLEAN;
        for (String urlParts : ChatConfiguration.getInstance().LINK_SPAM_WORDS){
            if (message.getMessage().contains(urlParts)) {
                return SpamType.URL_SENT;
            }
        }
        return SpamType.CLEAN;
    }

    public MessageContainLinkSpamFilter() {
        this.allowUrls = ChatConfiguration.getInstance().ALLOW_URLS;
    }

    public boolean isAllowUrls() {
        return allowUrls;
    }

    public void setAllowUrls(boolean allowUrls) {
        this.allowUrls = allowUrls;
    }
}
