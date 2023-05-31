package jp.konosuba.notificationserver.data.user.authuser;

import jp.konosuba.notificationserver.data.user.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthUserRepository extends JpaRepository<AuthUserEntity,Long> {
    AuthUserEntity getAuthUserEntitiesByCodeAndUser(Integer code, User user);
}
