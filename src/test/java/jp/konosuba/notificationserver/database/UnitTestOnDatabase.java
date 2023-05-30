package jp.konosuba.notificationserver.database;

import jp.konosuba.notificationserver.user.user.Role;
import jp.konosuba.notificationserver.user.user.User;
import jp.konosuba.notificationserver.user.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.Extension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@DataJpaTest
@ExtendWith(SpringExtension.class)
public class UnitTestOnDatabase {

    @Autowired
    private UserRepository userRepository;


    @Test
    public void registerNullableUser(){
        User user= new User();
        userRepository.save(user);
        assert user.getId()==null;
    }

    @Test
    public void registerUser(){
        User user= new User();
        user.setPhone("800000000000");
        user.setRole(Role.USER);

        userRepository.save(user);

        assert user.getId()==null;
    }


    @Test
    public void registerDoubleUser(){
        User user= new User();
        user.setPhone("800000000000");
        user.setRole(Role.USER);


        User user1= new User();
        user1.setPhone("800000000000");
        user1.setRole(Role.USER);

        userRepository.save(user);
        userRepository.save(user1);

        assert user1.getId()==null;
    }


}
