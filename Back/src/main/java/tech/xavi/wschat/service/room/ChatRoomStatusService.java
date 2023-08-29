package tech.xavi.wschat.service.room;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import tech.xavi.wschat.entity.ChatUserStatus;
import tech.xavi.wschat.exception.ChatError;
import tech.xavi.wschat.exception.ChatRuntimeException;
import tech.xavi.wschat.model.join.JoinInfo;
import tech.xavi.wschat.repository.ChatRoomRepository;
import tech.xavi.wschat.repository.ChatUserStatusRepository;

@RequiredArgsConstructor
@Service
public class ChatRoomStatusService {

    private final ChatRoomRepository roomRepository;
    private final ChatUserStatusRepository chatUserStatusRepository;


    public JoinInfo getJoinInfo(String roomId){
        return roomRepository
                .findById(roomId)
                .map( chatRoom ->
                    JoinInfo.builder()
                            .roomId(chatRoom.getId())
                            .roomName(chatRoom.getRoomTopic())
                            .maxCapacity(chatRoom.getUsersLimit())
                            .subscribedUsers(chatRoom.inscribedUsersSize())
                            .creationDate(chatRoom.getCreationDate())
                            .available(!chatRoom.isFull())
                            .build()
                )
                .orElseThrow( () -> new ChatRuntimeException(ChatError.RoomIdNotFound, HttpStatus.BAD_REQUEST));
    }

    public boolean isUserInscribedToChatRoom(String userId, String roomId){
        return chatUserStatusRepository.findById(
                new ChatUserStatus.ChatUserStatusId(userId,roomId)
        ).isPresent();
    }

}
