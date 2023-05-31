package jp.konosuba.notificationserver.controllers.mailing.service;

import jp.konosuba.notificationserver.controllers.mailing.responses.MailingResponse;
import jp.konosuba.notificationserver.data.codes.Code;
import jp.konosuba.notificationserver.data.messages.MessageRepository;
import jp.konosuba.notificationserver.data.user.user.User;
import jp.konosuba.notificationserver.jobRunrs.QueueJobRunr;
import jp.konosuba.notificationserver.utils.StringUtils;
import org.jobrunr.scheduling.JobScheduler;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public record MailingService(JobScheduler jobScheduler, QueueJobRunr queueJobRunr, MessageRepository messageRepository) {

    public ResponseEntity<MailingResponse> sendMessage(Long id) {
        User user = StringUtils.getUser();

        var message = messageRepository.getMessageEntitiesByIdAndUser(id,user);
        if(message == null){
            return ResponseEntity.status(Code.NOT_FOUND).body(null);
        }

        jobScheduler.enqueue(
                () -> queueJobRunr.execute(user,message));



        return null;

    }
}
