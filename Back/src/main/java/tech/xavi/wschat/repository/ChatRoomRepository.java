package tech.xavi.wschat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.xavi.wschat.entity.ChatRoom;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, String> {

    boolean existsByIdAndSaveActivityIsTrue(String id);

}
