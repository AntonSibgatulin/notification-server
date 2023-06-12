package jp.konosuba.notificationserver.jobRunrs;

import com.google.gson.Gson;
import jp.konosuba.notificationserver.NotificationServerApplication;
import jp.konosuba.notificationserver.data.contact.Contacts;
import jp.konosuba.notificationserver.data.messages.MessageObject;
import jp.konosuba.notificationserver.data.user.user.User;
import jp.konosuba.notificationserver.utils.ClassUtils;
import jp.konosuba.notificationserver.utils.StringUtils;
import org.jobrunr.jobs.annotations.Job;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;

@Service
public class QueueJobRunr extends Thread {
    private Logger logger = LoggerFactory.getLogger(getClass());


    private ExecutorService executorService;
    private LinkedBlockingDeque<JSONObject> list = new LinkedBlockingDeque<>();


    private final RedisTemplate<String, String> redisTemplate;
    private ValueOperations<String, String> valueOps;
    private final KafkaTemplate<String, String> kafkaTemplate;


    public QueueJobRunr(RedisTemplate<String, String> redisTemplate, KafkaTemplate<String, String> kafkaTemplate) {
        this.redisTemplate = redisTemplate;
        this.kafkaTemplate = kafkaTemplate;
        this.valueOps = redisTemplate.opsForValue();
        executorService = Executors.newFixedThreadPool(NotificationServerApplication.count_of_thread_to_put_in_poll);
        super.start();
    }
    @Job(name = "addMessageToQueueInKafka")
    public void execute2(Contacts[] messageEntity){
        System.out.println("Hello");
    }

    @Job(name = "addMessageToQueueInKafka")
    public void execute(User user, MessageObject messageEntity) {
        String messageData = StringUtils.getTypeOfMessage(messageEntity.getTypeMessage())+";"+messageEntity.getMessage();
        
        String id = "id"+messageEntity.getId();
        /*Jedis jedis = new Jedis();
        jedis.set(id,messageData);
        jedis.close();

         */
        putValue(id,messageData);


        for (int x = 0; x < messageEntity.getContacts().length; x++) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("typeOperation","send");
            jsonObject.put("contact", new JSONObject(ClassUtils.fromObjectToJson(messageEntity.getContacts()[x])));
            jsonObject.put("messageId",id);
            jsonObject.put("type","email");
            jsonObject.put("userId",user.getId());
            //if(x==messageEntity.getContacts().length-1){
            //    jsonObject.put("lastOne",true);
            //}

            kafkaTemplate.send(NotificationServerApplication.name_of_topic, jsonObject.toString());

            //System.out.println(jsonObject);
            //list.offer(jsonObject);
        }
        
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("typeOperation","end_send");
        jsonObject.put("messageId",id);
        jsonObject.put("userId",user.getId());
        kafkaTemplate.send(NotificationServerApplication.name_of_topic, jsonObject.toString());
        
        //list.offer(jsonObject);


    }




    @Override
    public void run() {
        while (true) {
            if (this.list.isEmpty() == false) {
                JSONObject jsonObject = list.poll();

                if(jsonObject == null){
                    continue;
                }

                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {

                           //kafkaTemplate.send(NotificationServerApplication.name_of_topic, jsonObject.toString());

                    }
                };
                executorService.execute(runnable);
            }
        }
    }

    public void putValue(final String key, final String value) {
        valueOps.set(key, value);
    }
    public String getMessage(String key) {
        return valueOps.get(key);
    }
}
