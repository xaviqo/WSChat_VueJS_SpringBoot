package tech.xavi.wschat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.xavi.wschat.entity.ChatUser;
import tech.xavi.wschat.model.projection.UserNicknameAvatarProjection;

import java.util.Optional;

@Repository
public interface ChatUserRepository extends JpaRepository<ChatUser,String> {
    Optional<UserNicknameAvatarProjection> findUserNicknameAvatarProjectionById(String id);
}

