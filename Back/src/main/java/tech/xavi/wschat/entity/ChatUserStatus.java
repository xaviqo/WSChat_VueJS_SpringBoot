package tech.xavi.wschat.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Entity
public class ChatUserStatus {

    @EmbeddedId
    private ChatUserStatusId id;

    @Column
    private boolean connected;


    public String getRoomId(){
        return this.id.getChatRoomId();
    }

    public String getChatUserId(){
        return this.id.getChatUserId();
    }

    public ChatUserStatus(String userId, String roomId) {
        this.id = new ChatUserStatusId(userId, roomId);
        this.connected = false;
    }

    public ChatUserStatus(String userId, String roomId, boolean connected) {
        this.id = new ChatUserStatusId(userId, roomId);
        this.connected = connected;
    }

    public ChatUserStatus() {
    }

    @Embeddable
    @Data
    public static class ChatUserStatusId implements Serializable {
        @Column(name = "room_id")
        private String chatRoomId;

        @Column(name = "user_id")
        private String chatUserId;

        public ChatUserStatusId(String chatUserId, String chatRoomId) {
            this.chatRoomId = chatRoomId;
            this.chatUserId = chatUserId;
        }

        public ChatUserStatusId() {
        }

        @Override
        public int hashCode() {
            return Objects.hash(chatRoomId, chatUserId);
        }
    }
}

