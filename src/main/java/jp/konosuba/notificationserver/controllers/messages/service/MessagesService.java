package jp.konosuba.notificationserver.controllers.messages.service;

import jp.konosuba.notificationserver.controllers.messages.requests.MessageRequest;
import jp.konosuba.notificationserver.controllers.messages.responses.MessagesListResponse;
import jp.konosuba.notificationserver.controllers.messages.responses.MessagesResponse;
import jp.konosuba.notificationserver.data.codes.Code;
import jp.konosuba.notificationserver.data.contact.Contacts;
import jp.konosuba.notificationserver.data.contact.ContactsRepository;
import jp.konosuba.notificationserver.data.messages.MessageEntity;
import jp.konosuba.notificationserver.data.messages.MessageRepository;
import jp.konosuba.notificationserver.data.user.user.User;
import jp.konosuba.notificationserver.utils.StringUtils;
import org.aspectj.bridge.Message;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public record MessagesService(MessageRepository messageRepository, ContactsRepository contactsRepository) {


    public List<MessageEntity> getAllMyMessages() {
        var user = StringUtils.getUser();
        List<MessageEntity> messages = messageRepository.findAllByUser(user);
        return messages;
    }






    private List<Contacts> loadListOfContacts(List<Long> list, User user) {
        List<Contacts> lists = new ArrayList<>();

        for (Long id : list) {
            Contacts contact = contactsRepository.getContactsByUserAndId(user, id);
            if (contact == null) continue;

            lists.add(contact);

        }
        return lists;

    }

    public ResponseEntity<MessagesResponse> deleteMessage(Long id) {
        var user = StringUtils.getUser();
        var message = messageRepository.getMessageEntitiesByIdAndUser(id,user);
        if (message == null){
            return ResponseEntity.status(Code.NOT_FOUND).body(new MessagesResponse("Message not found",Code.NOT_FOUND));
        }
        messageRepository.delete(message);
        return ResponseEntity.status(Code.OK).body(new MessagesResponse("",200));
    }

    public ResponseEntity<MessageEntity> getMessageById(Long id) {
        var user = StringUtils.getUser();
        var message = messageRepository.getMessageEntitiesByIdAndUser(id,user);
        if(message == null){
            return ResponseEntity.status(Code.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(Code.OK).body(message);
    }


    public ResponseEntity<MessagesResponse> addNewMessage(MessageRequest messageRequest) {
        var user = StringUtils.getUser();
        var message = StringUtils.generateMessage(messageRequest);

        //check message on valid

        List<Contacts> contactsList = loadListOfContacts(messageRequest.getContacts(),user);
        message.setContacts(contactsList);
        message.setUser(user);
        messageRepository.save(message);

        return ResponseEntity.status(Code.OK).body(new MessagesResponse("ok",Code.OK));

    }


    public ResponseEntity<MessagesResponse> editMessage(MessageRequest messageRequest, Long id) {
        var user = StringUtils.getUser();
        var message = messageRepository.getMessageEntitiesByIdAndUser(id,user);
        if(message == null){
            return ResponseEntity.status(Code.NOT_FOUND).body(null);
        }

        var messageNew = StringUtils.generateMessage(messageRequest);

        //check message on valid

        List<Contacts> contactsList = loadListOfContacts(messageRequest.getContacts(),user);
        messageNew.setContacts(contactsList);
        messageNew.setUser(message.getUser());
        messageNew.setId(message.getId());

        messageRepository.save(messageNew);



        return ResponseEntity.ok(new MessagesResponse("ok",Code.OK));
    }
}
