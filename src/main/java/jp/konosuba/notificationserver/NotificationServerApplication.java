package jp.konosuba.notificationserver;

import jp.konosuba.notificationserver.data.contact.Contacts;
import jp.konosuba.notificationserver.data.contact.ContactsRepository;
import jp.konosuba.notificationserver.data.messages.MessageRepository;
import jp.konosuba.notificationserver.data.user.user.User;
import jp.konosuba.notificationserver.data.user.user.UserRepository;
import jp.konosuba.notificationserver.utils.ClassUtils;
import org.json.JSONObject;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

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

    /*
    @Bean
    CommandLineRunner commandLineRunner(UserRepository userRepository,ContactsRepository contactsRepository){
        return args -> {
            User user = userRepository.getUserById(1L);
            List<Contacts> contacts = contactsRepository.findAllByUser(user);

            System.out.println(ClassUtils.fromObjectToJson(contacts));
            List<Contacts> contactsList = new ArrayList<>();
            for (var i =0;i<1500;i++){
                var contact = new Contacts();
                contact.setName("Test");
                contact.setEmail("test_"+i+"@test.test.ru");
                contact.setUser(user);
                contact.setWs(false);
                contact.setTg(false);
                contact.setVk(false);
                contact.setRelative(false);
                contact.setPhone("88888888888");
                contactsList.add(contact);
            }
            contactsRepository.saveAll(contactsList);
            System.out.println("OK All contacts saved");

        };
    }

     */


}
