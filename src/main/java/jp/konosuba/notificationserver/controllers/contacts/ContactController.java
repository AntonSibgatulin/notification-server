package jp.konosuba.notificationserver.controllers.contacts;

import jakarta.validation.Valid;
import jp.konosuba.notificationserver.controllers.contacts.requests.ImportContactPhoneRequest;
import jp.konosuba.notificationserver.controllers.contacts.responses.AllContactsResponse;
import jp.konosuba.notificationserver.controllers.contacts.responses.ContactResponse;
import jp.konosuba.notificationserver.controllers.contacts.service.ContactService;
import jp.konosuba.notificationserver.controllers.contacts.util.ContactModel;
import jp.konosuba.notificationserver.data.contact.Contacts;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/contacts")
public class ContactController {

    private final ContactService contactService;



    @GetMapping("/getMyContacts")
    private ResponseEntity<AllContactsResponse> getMyContacts(){
        return this.contactService.getMyContacts();
    }

    @GetMapping("/importMyContactFromCellPhone")
    private ResponseEntity<ContactResponse> importContactFromCellPhone(@Valid @RequestBody ImportContactPhoneRequest importContactPhoneRequest){

        return this.contactService.importContactFromCellPhone(importContactPhoneRequest);
    }

    @GetMapping("/importContactFromCsv")
    private ResponseEntity<AllContactsResponse> getLoadedContacts(@RequestParam("file") MultipartFile multipartFile){
        return this.contactService.getLoadedContacts(multipartFile);
    }

    @GetMapping("/getContactById/{id}")
    private ResponseEntity<Contacts> getContactById(@PathVariable("id") Long id){
        return this.contactService.getContactById(id);
    }
    @PutMapping("/createContact")
    private ResponseEntity<ContactResponse> createContact(@Valid @RequestBody ContactModel contactModel){
        return this.contactService.createContact(contactModel);
    }

    @PutMapping("/editContact/{id}")
    private ResponseEntity<ContactResponse> editContact(@PathVariable("id") Long id,@Valid @RequestBody ContactModel contactModel){
        return this.contactService.editContact(id,contactModel);
    }

}
