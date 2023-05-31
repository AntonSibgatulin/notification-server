package jp.konosuba.notificationserver.controllers.mailing;

import jp.konosuba.notificationserver.controllers.mailing.responses.MailingResponse;
import jp.konosuba.notificationserver.controllers.mailing.service.MailingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor

@RestController
@RequestMapping("/v1/api/mailing")
public class MailingController {


    private final MailingService mailingService;

    @GetMapping("/sendMessage/{id}")
    public ResponseEntity<MailingResponse> sendMessage(@PathVariable Long id){
        return this.mailingService.sendMessage(id);

    }
}
