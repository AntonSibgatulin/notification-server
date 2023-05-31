package jp.konosuba.notificationserver.data.messages;

import jp.konosuba.notificationserver.data.user.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<MessageEntity,Long> {
    MessageEntity getMessageEntitiesByIdAndUser(Long id, User user);
    List<MessageEntity> findAllByUser(User user);
}
