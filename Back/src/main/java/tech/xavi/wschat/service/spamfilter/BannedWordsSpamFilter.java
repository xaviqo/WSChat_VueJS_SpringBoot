package tech.xavi.wschat.service.spamfilter;

import lombok.Data;
import tech.xavi.wschat.configuration.ChatConfiguration;
import tech.xavi.wschat.entity.sub.ChatMessage;
import tech.xavi.wschat.entity.sub.SpamType;

import java.util.HashSet;
import java.util.Set;

@Data
public class BannedWordsSpamFilter implements SpamFilter{

    private final Set<String> bannedWordsInRoom;
    @Override
    public SpamType checkSpam(ChatMessage message) {
        if (ChatConfiguration.getInstance().BANNED_WORDS.stream().anyMatch(word -> message.getMessage().toLowerCase().contains(word))) {
            return SpamType.FFC_BANNED_WORD;
        }
        if (bannedWordsInRoom.stream().anyMatch(word -> message.getMessage().toLowerCase().contains(word))) {
            return SpamType.ROOM_BANNED_WORD;
        }
        return SpamType.CLEAN;
    }

    public BannedWordsSpamFilter() {
        bannedWordsInRoom = new HashSet<>();
    }

}
