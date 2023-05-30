package jp.konosuba.notificationserver.controllers.auth.service;

import jp.konosuba.notificationserver.codes.Code;
import jp.konosuba.notificationserver.controllers.auth.responses.AuthenticationResponse;
import jp.konosuba.notificationserver.user.authuser.AuthUserEntity;
import jp.konosuba.notificationserver.user.authuser.AuthUserRepository;
import jp.konosuba.notificationserver.user.reguser.RegUserEntity;
import jp.konosuba.notificationserver.user.reguser.RegUserRepository;
import jp.konosuba.notificationserver.user.user.User;
import jp.konosuba.notificationserver.user.user.UserRepository;
import jp.konosuba.notificationserver.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final RegUserRepository regUserRepository;
    private final AuthUserRepository authUserRepository;



    public ResponseEntity<AuthenticationResponse> auth(String phoneNumber) {

        var phone = StringUtils.formateCellPhone(phoneNumber);

        if (phone == null){
            return ResponseEntity.status(Code.INVALID_DATA).body(new AuthenticationResponse(Code.INVALID_DATA,"Phone number is not validate"));
        }

        User user = userRepository.getUserByPhone(phone);
        if (user == null){
            RegUserEntity regUser = StringUtils.regUserEntityGenerate(phoneNumber);
            regUserRepository.save(regUser);
            //ここでは、電話で電子メールまたはメッセージを送信する必要があります regUser.getCode();
            return ResponseEntity.status(Code.OK).body(new AuthenticationResponse(Code.REGISTER_CODE,"You must use reg link"));
        }else{
            AuthUserEntity authUserEntity = StringUtils.authUserEntityGenerate(user);
            authUserRepository.save(authUserEntity);

            return ResponseEntity.status(Code.OK).body(new AuthenticationResponse(Code.AUTH_CODE,"You must use auth link"));
        }

    }
}
