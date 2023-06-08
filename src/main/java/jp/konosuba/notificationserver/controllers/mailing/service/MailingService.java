package jp.konosuba.notificationserver.controllers.mailing.service;

import jp.konosuba.notificationserver.controllers.mailing.responses.MailingResponse;
import jp.konosuba.notificationserver.data.codes.Code;
import jp.konosuba.notificationserver.data.contact.Contacts;
import jp.konosuba.notificationserver.data.messages.MessageRepository;
import jp.konosuba.notificationserver.data.user.user.User;
import jp.konosuba.notificationserver.jobRunrs.QueueJobRunr;
import jp.konosuba.notificationserver.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import org.jobrunr.scheduling.BackgroundJob;
import org.jobrunr.scheduling.JobScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.print.DocFlavor;

@RequiredArgsConstructor
@Service
public class MailingService {

   // @Autowired
    private final   JobScheduler jobScheduler;


   // @Autowired
    private final QueueJobRunr queueJobRunr;
    //@Autowired
    private final MessageRepository messageRepository;

    public ResponseEntity<MailingResponse> sendMessage(Long id) {
        User user = StringUtils.getUser();

        var message = messageRepository.getMessageEntitiesByIdAndUser(id,user);
        var messageObject  = StringUtils.fromMessageEntityToMessageObject(message);

        if(message == null){
            return ResponseEntity.status(Code.NOT_FOUND).body(null);
        }
        BackgroundJob.enqueue(()->queueJobRunr.execute(user,messageObject));
        //BackgroundJob.enqueue(()->queueJobRunr.execute2());
        /*jobScheduler.enqueue(
                () -> queueJobRunr.execute(user,message));

         */



        return ResponseEntity.ok(new MailingResponse("ok",200));

    }
}
