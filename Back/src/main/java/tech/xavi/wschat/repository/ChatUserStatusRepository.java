package tech.xavi.wschat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import tech.xavi.wschat.entity.ChatUserStatus;

public interface ChatUserStatusRepository extends JpaRepository<ChatUserStatus, ChatUserStatus.ChatUserStatusId> {

    @Modifying
    @Query("UPDATE ChatUserStatus cus SET cus.connected = ?1 WHERE cus.id.chatRoomId = ?2 AND cus.id.chatUserId = ?3")
    void updateConnectedStatus(boolean connected, String chatRoomId, String chatUserId);

}