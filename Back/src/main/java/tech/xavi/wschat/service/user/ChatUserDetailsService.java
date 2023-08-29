package tech.xavi.wschat.service.user;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tech.xavi.wschat.entity.ChatUser;
import tech.xavi.wschat.repository.ChatUserRepository;

@Service
@AllArgsConstructor
public class ChatUserDetailsService implements UserDetailsService {

    private final ChatUserRepository chatUserRepository;
    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {

        ChatUser user = chatUserRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("USER ID NOT FOUND"));

        return User.builder()
                .username(user.getId())
                .password(user.getPassword())
                .disabled(!user.isEnabled())
                .accountExpired(false)
                .credentialsExpired(false)
                .accountLocked(false)
                .authorities(user.getAuthorities())
                .build();

    }

    public UserDetails obtainUserDetails(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                return (UserDetails) principal;
            }
        }
        return null;
    }

}
