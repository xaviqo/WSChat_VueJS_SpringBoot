package tech.xavi.wschat.service.room;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import tech.xavi.wschat.entity.ChatUser;
import tech.xavi.wschat.entity.sub.Avatar;
import tech.xavi.wschat.entity.sub.TokenPayload;
import tech.xavi.wschat.exception.ChatError;
import tech.xavi.wschat.exception.ChatRuntimeException;
import tech.xavi.wschat.model.join.JoinReq;
import tech.xavi.wschat.model.setup.UserStorageRes;
import tech.xavi.wschat.model.user.NewUser;
import tech.xavi.wschat.repository.ChatRoomRepository;
import tech.xavi.wschat.repository.ChatUserRepository;
import tech.xavi.wschat.service.user.JwtService;
import tech.xavi.wschat.service.user.UserService;
import tech.xavi.wschat.utils.IDGenerator;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatRoomJoinService {

    private final ChatUserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final UserService userService;
    private final JwtService jwtService;

    public UserStorageRes joinRoom(String roomId, JoinReq request){
        return chatRoomRepository.findById(roomId)
                .map( chatRoom -> {
                    if (chatRoom.isFull()) {
                        throw new ChatRuntimeException(ChatError.RoomIsFull, HttpStatus.UNAUTHORIZED);
                    }
                    final ChatUser user = userService.createUser(
                            NewUser.builder()
                                    .nickname(request.nickname())
                                    .password(IDGenerator.randomUserPassword())
                                    .avatar(Avatar.getAvatarFromStyle(request.avatar()))
                                    .build()
                    );
                    final TokenPayload tokenPayload = TokenPayload.builder()
                            .accessToken(jwtService.generateAccessToken(
                                    user.getUsername(),
                                    user.getId(),
                                    List.of(chatRoom.getId())
                            ))
                            .build();

                    chatRoom.joinRoom(user);
                    chatRoomRepository.save(chatRoom);
                    user.setNewChatRoom(chatRoom);
                    userRepository.save(user);

                    return UserStorageRes.builder()
                            .userNickname(user.getUsername())
                            .roomTopic(chatRoom.getRoomTopic())
                            .userId(user.getId())
                            .tokenPayload(tokenPayload)
                            .avatarStyle(request.avatar())
                            .chatId(chatRoom.getId())
                            .build();
                })
                .orElseThrow( () ->
                        new ChatRuntimeException(ChatError.FatalError,HttpStatus.BAD_REQUEST)
                );
    }
}
