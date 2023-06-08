package jp.konosuba.notificationserver.controllers.messages;

import jakarta.validation.Valid;
import jp.konosuba.notificationserver.controllers.messages.requests.MessageRequest;
import jp.konosuba.notificationserver.controllers.messages.responses.MessagesListResponse;
import jp.konosuba.notificationserver.controllers.messages.responses.MessagesResponse;
import jp.konosuba.notificationserver.controllers.messages.service.MessagesService;
import jp.konosuba.notificationserver.data.messages.MessageEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/messages")
public class MessagesController {


    private final MessagesService messagesService;


    @GetMapping("/getMyMessages")
    public List<MessageEntity> getAllMyMessages() {

        return this.messagesService.getAllMyMessages();
    }


    @PutMapping("/addNewMessage")
    public ResponseEntity<MessagesResponse> addNewMessage(@Valid @RequestBody MessageRequest messageRequest) {
        return this.messagesService.addNewMessage(messageRequest);
    }

    @PutMapping("/editMessage/{id}")
    public ResponseEntity<MessagesResponse> editMessage(@Valid @RequestBody MessageRequest messageRequest, @PathVariable Long id) {
        return this.messagesService.editMessage(messageRequest,id);
    }

    @DeleteMapping("/deleteMessage/{id}")
    public ResponseEntity<MessagesResponse> deleteMessage(@PathVariable Long id){
        return this.messagesService.deleteMessage(id);

    }

    @GetMapping("/getMessage/{id}")
    public ResponseEntity<MessageEntity> getMessageById(@PathVariable Long id){
        return this.messagesService.getMessageById(id);
    }

}
