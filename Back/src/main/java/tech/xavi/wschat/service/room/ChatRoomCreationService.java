package tech.xavi.wschat.service.room;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import tech.xavi.wschat.configuration.ChatConfiguration;
import tech.xavi.wschat.entity.ChatRoom;
import tech.xavi.wschat.entity.ChatUser;
import tech.xavi.wschat.entity.sub.Avatar;
import tech.xavi.wschat.entity.sub.TokenPayload;
import tech.xavi.wschat.exception.ChatError;
import tech.xavi.wschat.exception.ChatRuntimeException;
import tech.xavi.wschat.model.setup.CreateRoomReq;
import tech.xavi.wschat.model.setup.UserStorageRes;
import tech.xavi.wschat.model.user.NewUser;
import tech.xavi.wschat.repository.ChatRoomRepository;
import tech.xavi.wschat.repository.ChatUserRepository;
import tech.xavi.wschat.service.user.JwtService;
import tech.xavi.wschat.service.user.UserService;
import tech.xavi.wschat.utils.IDGenerator;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ChatRoomCreationService {

    private final ChatUserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final UserService userService;
    private final JwtService jwtService;

    public UserStorageRes createNewRom(CreateRoomReq request){

        // MODIFY IN FUTURE VERSIONS
        final boolean isProtectedRoom = false;
        final boolean showRoomInExplorer = false;

        if (request.roomCapacity() < 2)
            throw new ChatRuntimeException(ChatError.MinRoomSizeExceeded, HttpStatus.BAD_REQUEST);

        if (request.roomCapacity() > ChatConfiguration.getInstance().MAX_ROOM_CAPACITY)
            throw new ChatRuntimeException(ChatError.MaxRoomSizeExceeded,HttpStatus.BAD_REQUEST);

        final ChatUser admin = userService.createUser(
                NewUser.builder()
                        .nickname(request.nickname())
                        .password(IDGenerator.randomUserPassword())
                        .avatar(Avatar.getAvatarFromStyle(request.avatarStyle()))
                        .build()
        );

        final String roomPassword = isProtectedRoom ? IDGenerator.randomRoomPassword():null;

        final ChatRoom chatRoom = ChatRoom.builder()
                .id(IDGenerator.randomRoomId())
                .roomTopic(request.topic())
                .adminId(admin.getId())
                .password(roomPassword)
                .usersLimit(request.roomCapacity())
                .showInExplorer(showRoomInExplorer)
                .creationDate(LocalDateTime.now())
                .lastActivity(LocalDateTime.now())
                .saveActivity(true)
                .inscribedUsers(new HashSet<>())
                .bannedUsers(new HashSet<>())
                .activityHistory(new HashSet<>())
                .useSpamFilter(true)
                .build();

        if (!ChatConfiguration.getInstance().onlyAZ09RegEx(chatRoom.getRoomTopic()) || chatRoom.getRoomTopic().length() > 80) {
            chatRoom.setRoomTopic(ChatConfiguration.getInstance().DEFAULT_ROOM_TOPIC+" #"+chatRoom.getId());
        }

        final TokenPayload tokenPayload = TokenPayload
                .builder()
                .accessToken(jwtService.generateAccessToken(
                        admin.getUsername(),
                        admin.getId(),
                        List.of(chatRoom.getId())))
                .build();

        chatRoom.joinRoom(admin);
        chatRoomRepository.save(chatRoom);
        admin.setNewChatRoom(chatRoom);
        userRepository.save(admin);

        return UserStorageRes.builder()
                .userNickname(admin.getUsername())
                .roomTopic(chatRoom.getRoomTopic())
                .userId(admin.getId())
                .tokenPayload(tokenPayload)
                .avatarStyle(request.avatarStyle())
                .chatId(chatRoom.getId())
                .build();
    }
}
