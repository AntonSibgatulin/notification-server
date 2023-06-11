package jp.konosuba.notificationserver.data.messages;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageSendRepository extends JpaRepository<MessageSendEntity,Long> {
}
