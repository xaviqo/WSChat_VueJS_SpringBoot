package tech.xavi.wschat.service.room;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import tech.xavi.wschat.repository.ChatActivityRepository;
import tech.xavi.wschat.service.user.ChatUserDetailsService;

@RequiredArgsConstructor
@Service
public class ChatRoomMessageService {

    private final ChatActivityRepository chatActivityRepository;
    private final ChatRoomStatusService chatRoomStatusService;
    private final ChatUserDetailsService chatUserDetailsService;

    public Object getMessageHistory(String roomId) {
        UserDetails userDetails = chatUserDetailsService.obtainUserDetails();
        System.out.println(userDetails.getUsername());
        return null;
    }
}
