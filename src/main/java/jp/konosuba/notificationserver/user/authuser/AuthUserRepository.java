package jp.konosuba.notificationserver.user.authuser;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthUserRepository extends JpaRepository<AuthUserEntity,Long> {
}
