package tech.xavi.wschat.service.websocket;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import tech.xavi.wschat.entity.ChatActivity;
import tech.xavi.wschat.entity.ChatRoom;
import tech.xavi.wschat.entity.sub.ChatMessage;
import tech.xavi.wschat.entity.sub.MessageType;
import tech.xavi.wschat.entity.sub.SpamType;
import tech.xavi.wschat.model.ws.Activity;
import tech.xavi.wschat.repository.ChatActivityRepository;
import tech.xavi.wschat.repository.ChatRoomRepository;
import tech.xavi.wschat.repository.ChatUserRepository;
import tech.xavi.wschat.utils.IDGenerator;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ActivityMessageServiceWS {

    private final String WS_LOBBY_TOPIC = "/topic/messages/";
    private final SpamServiceWS webSocketSpamService;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatUserRepository chatUserRepository;
    private final ChatActivityRepository chatActivityRepository;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Transactional
    public void prepareMessage(ChatMessage message) {

        message.setTimeStamp(LocalDateTime.now());

        if (MessageType.MESSAGE.equals(message.getMessageType())){
            final SpamType spamType = webSocketSpamService.filterSpam(message);
            message.setSpamType(spamType);
            if (spamType != SpamType.CLEAN){
                message.setMessageType(MessageType.SPAM);
            }
        }

        ChatActivity activity = ChatActivity.builder()
                .activityId(IDGenerator.generateActivityId(message.getTimeStamp()))
                .chatUser(chatUserRepository.getReferenceById(message.getUserId()))
                .messageType(message.getMessageType())
                .spamType(message.getSpamType())
                .sentDate(LocalDateTime.now())
                .message(webSocketSpamService.prepareMessage(message))
                .build();

        sendActivity(activity,getMessageDestination(message.getRoomId()));
        saveActivity(activity,message.getRoomId());
    }

    //TODO: When user connects, call this method
    public void getRoomActivity(String roomId){
//        final TreeMap<Long, ChatActivity> chatActivity = chatRoomRepository.getActivityHistory(roomId);
//        final ChatUser chatUser = authService.getChatUser();
//
//        if (!chatRoom.isUserInscribed(chatUser.getId())){
//            throw new ChatRuntimeException(ChatError.RoomNotValid, HttpStatus.UNAUTHORIZED);
//        }
//
//        return ChatRoomActivityResDTO.builder()
//                .roomId(roomId)
//                .chatActivity(new TreeSet<>(chatActivity.values()))
//                .build();
    }

    public void sendActivity(ChatActivity activity, String destination){
        simpMessagingTemplate.convertAndSend(
                destination,
                Activity
                        .builder()
                        .activityId(activity.getActivityId())
                        .userId(activity.getChatUser().getId())
                        .messageType(activity.getMessageType())
                        .spamType(activity.getSpamType())
                        .sentDate(activity.getSentDate())
                        .message(activity.getMessage())
                        .build()
        );
    }

    @Transactional
    public void saveActivity(ChatActivity activity, String roomId){
        final boolean isSaveActivity = chatRoomRepository.existsByIdAndSaveActivityIsTrue(roomId);
        if (isSaveActivity){
            activity.setChatRoom(chatRoomRepository.getReferenceById(roomId));
            chatActivityRepository.save(activity);
            Optional<ChatRoom> chatRoom = chatRoomRepository.findById(roomId);
            chatRoom.ifPresent(room -> System.out.println(room.getActivityHistory()));
        }
    }

    public String getMessageDestination(String roomId){
        return WS_LOBBY_TOPIC+roomId;
    }

}
