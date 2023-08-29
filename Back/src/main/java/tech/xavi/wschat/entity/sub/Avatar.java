package tech.xavi.wschat.entity.sub;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import tech.xavi.wschat.exception.ChatError;
import tech.xavi.wschat.exception.ChatRuntimeException;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
public enum Avatar {

    ADVENTURER("adventurer"),
    ADVENTURER_NEUTRAL("adventurer-neutral"),
    BIG_EARS("big-ears"),
    BIG_EARS_NEUTRAL("big-ears-neutral"),
    BIG_SMILE("big-smile"),
    BOTTTS("bottts"),
    CROODLES("croodles"),
    CROODLES_NEUTRAL("croodles-neutral"),
    IDENTICON("identicon"),
    INITIALS("initials"),
    MICAH("micah"),
    MINIAVS("miniavs"),
    OPEN_PEEPS("open-peeps"),
    PERSONAS("personas"),
    PIXEL_ART("pixel-art"),
    PIXEL_ART_NEUTRAL("pixel-art-neutral");

    private final String avatar;

    public static Set<String> availableAvatars(){
        return Arrays.stream(Avatar.values())
                .map(Avatar::getAvatar)
                .collect(Collectors.toSet());
    }

    public static Avatar getAvatarFromStyle(String style){
        for (Avatar avatar : Avatar.values()){
            if (avatar.getAvatar().equals(style)) return avatar;
        };
        throw new ChatRuntimeException(ChatError.InvalidAvatar, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
