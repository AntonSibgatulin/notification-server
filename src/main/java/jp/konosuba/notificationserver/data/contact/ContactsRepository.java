package jp.konosuba.notificationserver.data.contact;

import jp.konosuba.notificationserver.data.user.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface  ContactsRepository extends JpaRepository<Contacts,Long> {
    List<Contacts> findAllByUser(User user);
    Contacts getContactsByUserAndId(User user,Long id);

}
