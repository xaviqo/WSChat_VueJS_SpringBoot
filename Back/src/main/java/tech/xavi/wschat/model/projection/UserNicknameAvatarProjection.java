package tech.xavi.wschat.model.projection;

import tech.xavi.wschat.entity.sub.Avatar;

public interface UserNicknameAvatarProjection {
    Avatar getAvatar();
    String getUsername();
}
