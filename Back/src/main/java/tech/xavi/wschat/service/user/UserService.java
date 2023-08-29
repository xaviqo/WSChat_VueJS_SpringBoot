package tech.xavi.wschat.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tech.xavi.wschat.configuration.ChatConfiguration;
import tech.xavi.wschat.entity.ChatRoom;
import tech.xavi.wschat.entity.ChatUser;
import tech.xavi.wschat.exception.ChatError;
import tech.xavi.wschat.exception.ChatRuntimeException;
import tech.xavi.wschat.model.user.NewUser;
import tech.xavi.wschat.model.user.RoomUser;
import tech.xavi.wschat.model.user.RoomUsersRes;
import tech.xavi.wschat.repository.ChatRoomRepository;
import tech.xavi.wschat.repository.ChatUserRepository;
import tech.xavi.wschat.utils.IDGenerator;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserService {

    private final ChatUserRepository chatUserRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final PasswordEncoder passwordEncoder;

    public ChatUser createUser(NewUser newUser){

        if (newUser.nickname() == null
                || newUser.nickname().isBlank()
                || newUser.nickname().length() < ChatConfiguration.getInstance().MIN_NICK_LENGTH
        ){
            throw new ChatRuntimeException(ChatError.NicknameMinLength, HttpStatus.BAD_REQUEST);
        }
        if (newUser.nickname().length() > ChatConfiguration.getInstance().MAX_NICK_LENGTH){
            throw new ChatRuntimeException(ChatError.NicknameMaxLength, HttpStatus.BAD_REQUEST);
        }
        if (!ChatConfiguration.getInstance().onlyAZ09RegEx(newUser.nickname())){
            throw new ChatRuntimeException(ChatError.NicknameOnlyAZ09, HttpStatus.UNPROCESSABLE_ENTITY);
        }

        return chatUserRepository.save(ChatUser.builder()
                .username(newUser.nickname())
                .password(passwordEncoder.encode(newUser.password()))
                .id(IDGenerator.randomUserId())
                .avatar(newUser.avatar())
                .chatRooms(new HashSet<>())
                .build());

    }

    public RoomUsersRes getRoomUsers(String roomId) {
        ChatRoom chatRoom = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new ChatRuntimeException(ChatError.RoomIdNotFound, HttpStatus.BAD_REQUEST));

        Set<RoomUser> inscribedUsers = chatRoom.getInscribedUsers().stream()
                .flatMap(user -> chatUserRepository.findUserNicknameAvatarProjectionById(user.getChatUserId())
                        .stream()
                        .map(data -> RoomUser.builder()
                                .nickname(data.getUsername())
                                .avatar(data.getAvatar())
                                .id(user.getChatUserId())
                                .connected(user.isConnected())
                                .build()))
                .collect(Collectors.toSet());

        return new RoomUsersRes(inscribedUsers);
    }

}
