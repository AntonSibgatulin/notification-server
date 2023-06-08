package jp.konosuba.notificationserver.jobRunrs;

import com.google.gson.Gson;
import jp.konosuba.notificationserver.NotificationServerApplication;
import jp.konosuba.notificationserver.data.contact.Contacts;
import jp.konosuba.notificationserver.data.messages.MessageObject;
import jp.konosuba.notificationserver.data.user.user.User;
import jp.konosuba.notificationserver.utils.StringUtils;
import org.jobrunr.jobs.annotations.Job;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;

@Service
public class QueueJobRunr extends Thread {
    private Logger logger = LoggerFactory.getLogger(getClass());


    private ExecutorService executorService;
    private LinkedBlockingDeque<JSONObject> list = new LinkedBlockingDeque<>();


    private final RedisTemplate<String, String> redisTemplate;
    private final KafkaTemplate<String, String> kafkaTemplate;


    public QueueJobRunr(RedisTemplate<String, String> redisTemplate, KafkaTemplate<String, String> kafkaTemplate) {
        this.redisTemplate = redisTemplate;
        this.kafkaTemplate = kafkaTemplate;

        executorService = Executors.newFixedThreadPool(NotificationServerApplication.count_of_thread_to_put_in_poll);
        super.start();
    }
    @Job(name = "addMessageToQueueInKafka")
    public void execute2(Contacts[] messageEntity){
        System.out.println("Hello");
    }

    @Job(name = "addMessageToQueueInKafka")
    public void execute(User user, MessageObject messageEntity) {
       System.out.println("run job");
        String messageData = StringUtils.getTypeOfMessage(messageEntity.getTypeMessage())+";"+messageEntity.getMessage();
        String id = "id"+messageEntity.getId();

        putValue(id,messageData);

        for (int x = 0; x < messageEntity.getContacts().length; x++) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("typeOperation","send");
            jsonObject.put("contact",new Gson().toJson(messageEntity.getContacts()[x]));
            jsonObject.put("messageId",id);
            jsonObject.put("type","email");
            list.offer(jsonObject);
        }
    }




    @Override
    public void run() {
        while (true) {
            if (this.list.isEmpty() == false) {
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        JSONObject jsonObject = list.poll();
                        kafkaTemplate.send(NotificationServerApplication.name_of_topic, jsonObject.toString());

                    }
                };
                executorService.execute(runnable);
            }
        }
    }

    public void putValue(final String key, final String value) {
        redisTemplate.opsForValue().set(key, value);
    }

}
