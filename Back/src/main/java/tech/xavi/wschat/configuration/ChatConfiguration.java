package tech.xavi.wschat.configuration;


import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Setter
public class ChatConfiguration {

    public boolean IS_PRODUCTION;
    public String CORS_URL;
    public final String VERSION = "v1";
    public final String LOG_PREFIX = "[ FFC ] - ";
    public final String PROPERTIES_FILE = "/chatcfg.properties";
    public final String CHAT_STR_REGEX = "[a-zA-Z0-9]*";
    public String[] NO_FILTERED_ROUTES;
    public int MAX_ROOM_CAPACITY;
    public int MAX_ROOM_PWD_LENGTH;
    public int MAX_MSG_LENGTH;
    public int MIN_MSG_LENGTH;
    public int MAX_NICK_LENGTH;
    public int MIN_NICK_LENGTH;
    public int SEC_BETWEEN_MSG_DEFAULT;
    public Set<String> BANNED_WORDS;
    public Set<String> LINK_SPAM_WORDS;
    public boolean ALLOW_URLS;
    public int INITIAL_MAX_SPAM_POINTS;
    public int SPAM_POINTS_RECOVERY_SEC_DEFAULT;
    public String DEFAULT_ROOM_TOPIC;
    private static ChatConfiguration INSTANCE;

    public ChatConfiguration() {
        log.info(LOG_PREFIX+"Loading default configuration from "+PROPERTIES_FILE);
        try {
            final Properties properties = new Properties();
            properties.load(ChatConfiguration.class.getResourceAsStream(PROPERTIES_FILE));
            IS_PRODUCTION = properties.getProperty("freefastchat.cfg.production").equals("true");
            CORS_URL = IS_PRODUCTION ? properties.getProperty("freefastchat.cfg.url.cors.pro") : properties.getProperty("freefastchat.cfg.url.cors.dev");
            MAX_ROOM_CAPACITY = Integer.parseInt(properties.getProperty("freefastchat.cfg.max-room-capacity"));
            MAX_ROOM_PWD_LENGTH = Integer.parseInt(properties.getProperty("freefastchat.cfg.max-room-pwd-len"));
            MAX_MSG_LENGTH = Integer.parseInt(properties.getProperty("freefastchat.cfg.max-message-len"));
            MIN_MSG_LENGTH = Integer.parseInt(properties.getProperty("freefastchat.cfg.min-message-len"));
            MAX_NICK_LENGTH = Integer.parseInt(properties.getProperty("freefastchat.cfg.max-nickname-len"));
            MIN_NICK_LENGTH = Integer.parseInt(properties.getProperty("freefastchat.cfg.min-nickname-len"));
            SPAM_POINTS_RECOVERY_SEC_DEFAULT = Integer.parseInt(properties.getProperty("freefastchat.spam.points-recovery-sec"));
            INITIAL_MAX_SPAM_POINTS = Integer.parseInt(properties.getProperty("freefastchat.spam.max-spam-points"));
            SEC_BETWEEN_MSG_DEFAULT  = Integer.parseInt(properties.getProperty("freefastchat.spam.sec-between-message"));
            BANNED_WORDS = Arrays.stream(properties.getProperty("freefastchat.spam.banned-words").split(",")).collect(Collectors.toSet());
            LINK_SPAM_WORDS = Arrays.stream(properties.getProperty("freefastchat.spam.link-filter").split(",")).collect(Collectors.toSet());
            DEFAULT_ROOM_TOPIC = properties.getProperty("freefastchat.cfg.default-room-topic");
            ALLOW_URLS = properties.getProperty("freefastchat.spam.allow-urls-default").equalsIgnoreCase("true");
            NO_FILTERED_ROUTES = properties.getProperty("freefast.chat.routes.no-filter").split(",");
            log.info(LOG_PREFIX+"Default configuration successfully loaded");
            log.info(LOG_PREFIX+"Production mode is: ["+(IS_PRODUCTION ? "ON":"OFF")+"]");

        } catch (Exception e) {
            log.error(LOG_PREFIX+"ERROR READING "+this.getClass().getSimpleName()
                    +" PROPERTIES FILE");
            e.printStackTrace();
        }
    }

    public String[] getShouldNotFilterRoutes(){
        return this.NO_FILTERED_ROUTES;
    }

    public static synchronized ChatConfiguration getInstance() {
        if (INSTANCE == null) INSTANCE = new ChatConfiguration();
        return INSTANCE;
    }

    public boolean onlyAZ09RegEx(String str){
        return (str != null && !str.isBlank() && str.matches(CHAT_STR_REGEX));
    }

}
