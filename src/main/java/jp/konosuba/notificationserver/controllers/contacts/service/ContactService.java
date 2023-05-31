package jp.konosuba.notificationserver.controllers.contacts.service;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import jp.konosuba.notificationserver.data.codes.Code;
import jp.konosuba.notificationserver.data.contact.Contacts;
import jp.konosuba.notificationserver.data.contact.ContactsMapper;
import jp.konosuba.notificationserver.data.contact.ContactsRepository;
import jp.konosuba.notificationserver.controllers.contacts.requests.ImportContactPhoneRequest;
import jp.konosuba.notificationserver.controllers.contacts.responses.AllContactsResponse;
import jp.konosuba.notificationserver.controllers.contacts.responses.ContactResponse;
import jp.konosuba.notificationserver.controllers.contacts.util.ContactModel;
import jp.konosuba.notificationserver.data.user.user.User;
import jp.konosuba.notificationserver.utils.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;


@Service
public record ContactService(ContactsRepository contactsRepository,
                             ContactsMapper contactsMapper) {

    public ResponseEntity<AllContactsResponse> getMyContacts() {
        var user = StringUtils.getUser();

        List<Contacts> contactsList = contactsRepository.findAllByUser(user);

        return ResponseEntity.ok(new AllContactsResponse(contactsList));

    }

    public ResponseEntity<ContactResponse> importContactFromCellPhone(ImportContactPhoneRequest importContactPhoneRequest) {
        var user = StringUtils.getUser();

        if (importContactPhoneRequest.getContacts().size() == 0) {
            return ResponseEntity.ok(new ContactResponse("ok", 200));
        }
        List<Contacts> contactsList = new ArrayList<>();
        List<ContactModel> contactModelList = importContactPhoneRequest.getContacts();
        for (ContactModel contactModel : contactModelList) {
            Contacts contacts = contactsMapper.fromContactModeltoContacts(contactModel);
            contacts.setUser(user);
            contactsList.add(contacts);

        }
        saveListOfContacts(contactsList);

        return ResponseEntity.ok(new ContactResponse("ok", 200));


    }

    public ResponseEntity<AllContactsResponse> getLoadedContacts(MultipartFile file) {
        var user = StringUtils.getUser();
        if (file.isEmpty()) {
            return ResponseEntity.status(Code.FILE_IS_EMPTY).body(new AllContactsResponse());
        } else {
            try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {

                CsvToBean<Contacts> csvToBean = new CsvToBeanBuilder(reader)
                        .withType(Contacts.class)
                        .withIgnoreLeadingWhiteSpace(true)
                        .build();
                List<Contacts> contacts = csvToBean.parse();

                //init user to all contacts
                for (Contacts contact : contacts){
                    contact.setUser(user);
                }



                saveListOfContacts(contacts);

            } catch (Exception ex) {
               System.out.println(ex);
               return ResponseEntity.status(Code.INVALID_DATA).body(new AllContactsResponse());
            }

        }
        return null;
    }


    public void saveListOfContacts(List<Contacts> contactsList){
        var count = 0;
        var unsuccessful=0;
        for (Contacts contacts : contactsList) {
            try {

                contactsRepository.save(contacts);
                count += 1;

            } catch (Exception e) {
                //ignore
                unsuccessful+=1;
                System.out.println(e);
            }

        }
        //return [count,successful];
    }

    public ResponseEntity<Contacts> getContactById(Long id) {
        User user = StringUtils.getUser();
        Contacts contacts = contactsRepository.getContactsByUserAndId(user,id);
        if(contacts==null){
            return ResponseEntity.status(Code.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(contacts);
    }

    public ResponseEntity<ContactResponse> createContact(ContactModel contactModel) {
        var contacts = contactsMapper.fromContactModeltoContacts(contactModel);
        var user = StringUtils.getUser();
        contacts.setUser(user);
        contactsRepository.save(contacts);
        return ResponseEntity.ok(new ContactResponse("ok",Code.OK));
    }

    public ResponseEntity<ContactResponse> editContact(Long id, ContactModel contactModel) {
        User user = StringUtils.getUser();
        Contacts contacts = contactsRepository.getContactsByUserAndId(user,id);
        if(contacts==null){
            return ResponseEntity.status(Code.NOT_FOUND).body(null);
        }
        var contactsNew = contactsMapper.fromContactModeltoContacts(contactModel);
        contactsNew.setUser(contacts.getUser());
        contactsNew.setId(contactsNew.getId());

        contactsRepository.save(contactsNew);

        return ResponseEntity.ok(new ContactResponse("ok",Code.OK));
    }
}
