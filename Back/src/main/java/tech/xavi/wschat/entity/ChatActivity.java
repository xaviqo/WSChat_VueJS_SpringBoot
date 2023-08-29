package tech.xavi.wschat.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import tech.xavi.wschat.entity.sub.MessageType;
import tech.xavi.wschat.entity.sub.SpamType;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ChatActivity implements Comparable<ChatActivity>{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long activityId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", referencedColumnName = "id", nullable = false)
    private ChatRoom chatRoom;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private ChatUser chatUser;
    @Column(nullable = false)
    private MessageType messageType;
    @Column(nullable = false)
    private SpamType spamType;
    @Column(length = 999, nullable = false)
    private String message;
    @Column(nullable = false)
    private LocalDateTime sentDate;


    @Override
    public int compareTo(ChatActivity otherActivity) {
        return Long.compare(
                this.activityId,
                otherActivity.activityId
        );
    }
}
