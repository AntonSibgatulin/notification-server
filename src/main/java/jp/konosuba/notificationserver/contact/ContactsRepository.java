package jp.konosuba.notificationserver.contact;

import jp.konosuba.notificationserver.user.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface  ContactsRepository extends JpaRepository<Contacts,Long> {
    List<Contacts> getContactsByUser(User user);
}
