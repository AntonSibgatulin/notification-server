package jp.konosuba.notificationserver.data.contact;

import io.lettuce.core.dynamic.annotation.Param;
import jp.konosuba.notificationserver.data.user.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface  ContactsRepository extends JpaRepository<Contacts,Long> {
    List<Contacts> findAllByUser(User user);
    Contacts getContactsByUserAndId(User user,Long id);


    @Query("SELECT c FROM Contacts c where c.user.id in :ids")
    List<Contacts> findByUserIds(@Param("ids") List<Long> ids);
}
