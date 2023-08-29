package tech.xavi.wschat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tech.xavi.wschat.entity.ChatActivity;

import java.util.List;

public interface ChatActivityRepository extends JpaRepository<ChatActivity,Long> {

    @Query("SELECT ca FROM ChatActivity ca WHERE ca.chatRoom.id = :roomId")
    List<ChatActivity> getMessageHistoryFromRoomId(@Param("roomId") String roomId);

}
