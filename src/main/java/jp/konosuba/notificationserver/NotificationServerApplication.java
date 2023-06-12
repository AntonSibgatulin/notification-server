package jp.konosuba.notificationserver;

import org.json.JSONObject;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;

import java.io.*;

@EnableBatchProcessing
@SpringBootApplication
public class NotificationServerApplication {

    public static Integer count_of_thread_to_put_in_poll;
    public static String name_of_topic;
    public static String redis_host;
    public static Integer redis_port;
    public static Integer count_of_consumers;
    public static String topicMainControllerReader;

    public static void main(String[] args) {
        JSONObject jsonObject = readConfigureFile("configure/config.json");

        Integer countThreadInPoll = jsonObject.getInt("countThreadInPoll");
        String nameOfTopic = jsonObject.getString("name_of_topic");
        String redisHost = jsonObject.getString("redis_host");
        Integer redisPort = jsonObject.getInt("redis_port");
        Integer countOfConsumers = jsonObject.getInt("count_of_consumers");
        String topicMainControllerReaderStatic = jsonObject.getString("topicMainControllerReader");

        count_of_thread_to_put_in_poll = countThreadInPoll;
        name_of_topic = nameOfTopic;
        redis_host = redisHost;
        redis_port = redisPort;
        count_of_consumers = countOfConsumers;
        topicMainControllerReader = topicMainControllerReaderStatic;


        SpringApplication.run(NotificationServerApplication.class, args);
    }


    public static JSONObject readConfigureFile(String file) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(file)));
            String all = "";
            String pie = null;
            while ((pie = bufferedReader.readLine()) != null) {
                all += pie;
            }
            return new JSONObject(all);
        } catch (FileNotFoundException e) {
            System.out.println("File not found " + file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //@Bean
    CommandLineRunner commandLineRunner(KafkaTemplate<String,String> kafkaTemplate){
        return args ->
        {
            for (int i = 0;i<10_000_000;i++)
            kafkaTemplate.send("notificator","hello kafka "+i);
        };
    }
}
