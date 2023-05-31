package jp.konosuba.notificationserver.data.user.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findUserByPhone(String phone);
    User getUserByPhone(String phone);
}
