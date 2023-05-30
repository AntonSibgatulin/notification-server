package jp.konosuba.notificationserver.controllers.contacts;

import jp.konosuba.notificationserver.controllers.contacts.responses.AllContactsResponse;
import jp.konosuba.notificationserver.controllers.contacts.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/contacts")
public class ContactController {

    private final ContactService contactService;



    @GetMapping("/getMyContacts")
    private ResponseEntity<AllContactsResponse> getMyContacts(){

        return contactService.getMyContacts();
    }
}
