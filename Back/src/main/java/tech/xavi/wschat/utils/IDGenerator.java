package tech.xavi.wschat.utils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

public class IDGenerator {

    public static String randomRefreshToken(){
        return randomUUID();
    }
    public static String randomUserId(){
        final String uuid = randomUUID();
        return uuid.substring(uuid.length()-8);
    }

    public static String randomUserPassword(){
        final String uuid = randomUUID();
        return uuid.substring(uuid.length()-8);
    }

    public static String randomRoomId(){
        final String uuid = randomUUID();
        return uuid.substring(uuid.length()-8);
    }

    public static String randomRoomPassword(){
        final String uuid = randomUUID();
        return uuid.substring(uuid.length()-16);
    }

    public static long generateActivityId(LocalDateTime timeStamp){
        return timeStamp.toInstant(ZoneOffset.UTC).toEpochMilli();
    }

    private static String randomUUID(){
        return UUID.randomUUID().toString().replace("-", "").toLowerCase();
    }
}
