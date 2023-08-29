package tech.xavi.wschat.service.room;

import org.springframework.stereotype.Service;
import tech.xavi.wschat.configuration.ChatConfiguration;
import tech.xavi.wschat.entity.sub.Avatar;
import tech.xavi.wschat.model.setup.AppConfigurationRes;

@Service
public class ChatRoomConfigurationService {

    public AppConfigurationRes getAppConfiguration(){
        return AppConfigurationRes.builder()
                .maxRoomCapacity(ChatConfiguration.getInstance().MAX_ROOM_CAPACITY)
                .availableAvatars(Avatar.availableAvatars())
                .build();
    }
}
