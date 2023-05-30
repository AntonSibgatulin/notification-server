package jp.konosuba.notificationserver.database;

import jp.konosuba.notificationserver.user.authuser.AuthUserEntity;
import jp.konosuba.notificationserver.user.authuser.AuthUserRepository;
import jp.konosuba.notificationserver.user.reguser.RegUserEntity;
import jp.konosuba.notificationserver.user.reguser.RegUserRepository;
import jp.konosuba.notificationserver.user.user.Role;
import jp.konosuba.notificationserver.user.user.User;
import jp.konosuba.notificationserver.user.user.UserRepository;
import jp.konosuba.notificationserver.utils.StringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.Extension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@DataJpaTest
@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UnitTestOnDatabase {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthUserRepository authUserRepository;

    @Autowired
    private RegUserRepository regUserRepository;


    @Test
    public void registerNullableUser(){
        User user= new User();
        try {
            userRepository.save(user);
        }catch(Exception e){

        }
        assert user.getId()==null;

    }

    @Test
    public void registerUser(){
        User user= new User();
        user.setPhone("800000000000");
        user.setRole(Role.USER);

        userRepository.save(user);

        assert user.getId()!=null;
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
        try {
            userRepository.save(user1);
        }catch (Exception e){
            //無視する

        }

        assert user1.getId()==null;
    }

    @Test
    public void saveAuthUser(){
        User user = new User();
        user.setPhone("800000000000");
        user.setRole(Role.USER);

        userRepository.save(user);
        AuthUserEntity authUserEntity = StringUtils.authUserEntityGenerate(user);
        authUserRepository.save(authUserEntity);
        assert authUserEntity.getId()!=null;

    }

    @Test
    public void saveRegUser(){
        RegUserEntity regUser = StringUtils.regUserEntityGenerate("79999999999");
        regUserRepository.save(regUser);

        assert regUser.getId()!=null;

    }


    @Test
    public void saveNullableRegUser(){
        RegUserEntity regUser = new RegUserEntity();
        try {
            regUserRepository.save(regUser);
        }catch (Exception e){}
        assert regUser.getId()==null;

    }
}
