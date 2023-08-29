package tech.xavi.wschat.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatRoom {

    @Id
    @Column(length = 8)
    private String id;
    @Column(length = 80, nullable = false)
    private String roomTopic;
    @Column(length = 8, nullable = false)
    private String adminId;
    @Column(length = 60)
    private String password;
    @Column(nullable = false)
    private int usersLimit;
    @Column(nullable = false)
    private boolean showInExplorer;
    @Column(nullable = false)
    private boolean useSpamFilter;
    @Column(nullable = false)
    private boolean saveActivity;
    @Column(nullable = false)
    private LocalDateTime creationDate;
    @Column(nullable = false)
    private LocalDateTime lastActivity;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Set<ChatUserStatus> inscribedUsers;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "banned_users",
            joinColumns = @JoinColumn(name = "room_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<ChatUser> bannedUsers;
    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<ChatActivity> activityHistory;

    public void joinRoom(ChatUser user){
        if (!isFull()) inscribedUsers.add(
                new ChatUserStatus(user.getId(),this.getId())
        );
    }

    public void saveActivity(ChatActivity chatActivity){
        activityHistory.add(chatActivity);
    }

    public void connectUser(String userId){
        inscribedUsers.add(new ChatUserStatus(userId,this.getId(),true));
    }

    public void disconnectUser(String userId){
        inscribedUsers.add(new ChatUserStatus(userId,this.getId(),false));
    }

    public boolean isFull(){
        return inscribedUsersSize() >= getUsersLimit();
    }

    public boolean isPasswordProtected(){
        return (getPassword() != null);
    }

    public int inscribedUsersSize(){
        return inscribedUsers.size();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


}
