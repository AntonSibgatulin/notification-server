package jp.konosuba.notificationserver.controllers.contacts.service;

import jp.konosuba.notificationserver.contact.Contacts;
import jp.konosuba.notificationserver.contact.ContactsRepository;
import jp.konosuba.notificationserver.controllers.contacts.responses.AllContactsResponse;
import jp.konosuba.notificationserver.user.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public record ContactService(ContactsRepository contactsRepository) {

    public ResponseEntity<AllContactsResponse> getMyContacts() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        List<Contacts> contactsList = contactsRepository.getContactsByUser(user);

        return ResponseEntity.ok(new AllContactsResponse(contactsList));

    }
}
