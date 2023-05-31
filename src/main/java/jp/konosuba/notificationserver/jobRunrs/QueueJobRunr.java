package jp.konosuba.notificationserver.jobRunrs;

import jp.konosuba.notificationserver.data.messages.MessageEntity;
import jp.konosuba.notificationserver.data.user.user.User;
import org.aspectj.bridge.Message;
import org.jobrunr.jobs.annotations.Job;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class QueueJobRunr {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Job(name = "addMessageToQueueInKafka")
    public void execute(User user, MessageEntity messageEntity) {

    }
}
