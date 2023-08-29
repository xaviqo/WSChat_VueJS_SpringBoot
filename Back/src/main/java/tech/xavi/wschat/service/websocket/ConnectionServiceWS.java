package tech.xavi.wschat.service.websocket;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.xavi.wschat.entity.ChatUserStatus;
import tech.xavi.wschat.repository.ChatRoomRepository;
import tech.xavi.wschat.repository.ChatUserStatusRepository;

import javax.transaction.Transactional;

@Slf4j
@AllArgsConstructor
@Service
public class ConnectionServiceWS {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatUserStatusRepository chatUserStatusRepository;

    public boolean isUserWSConnected(String userId, String roomId){
        return chatUserStatusRepository.findById(
                new ChatUserStatus.ChatUserStatusId(userId,roomId)
        ).map(ChatUserStatus::isConnected).orElse(false);
    }

    @Transactional
    public void switchUserStatus(String userId, String roomId, boolean status){
        chatUserStatusRepository.updateConnectedStatus(status,roomId,userId);
    }

}