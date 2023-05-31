package jp.konosuba.notificationserver.controllers.auth.service;

import jp.konosuba.notificationserver.data.codes.Code;
import jp.konosuba.notificationserver.config.JwtService;
import jp.konosuba.notificationserver.controllers.auth.responses.AuthenticationResponse;
import jp.konosuba.notificationserver.data.user.authuser.AuthUserEntity;
import jp.konosuba.notificationserver.data.user.authuser.AuthUserRepository;
import jp.konosuba.notificationserver.data.user.reguser.RegUserEntity;
import jp.konosuba.notificationserver.data.user.reguser.RegUserRepository;
import jp.konosuba.notificationserver.data.user.token.AuthToken;
import jp.konosuba.notificationserver.data.user.token.TokenRepository;
import jp.konosuba.notificationserver.data.user.user.Role;
import jp.konosuba.notificationserver.data.user.user.User;
import jp.konosuba.notificationserver.data.user.user.UserRepository;
import jp.konosuba.notificationserver.utils.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;



@Service
public record AuthenticationService(UserRepository userRepository,
                                    RegUserRepository regUserRepository,
                                    AuthUserRepository authUserRepository,
                                    TokenRepository tokenRepository,
                                    JwtService jwtService) {

    public ResponseEntity<AuthenticationResponse> auth(String phoneNumber) {

        var phone = StringUtils.formateCellPhone(phoneNumber);

        if (phone == null) {
            return ResponseEntity.status(Code.INVALID_DATA).body(new AuthenticationResponse(Code.INVALID_DATA, "Phone number is not validate", null));
        }

        User user = userRepository.getUserByPhone(phone);
        if (user == null) {
            RegUserEntity regUser = StringUtils.regUserEntityGenerate(phoneNumber);
            regUserRepository.save(regUser);
            //ここでは、電話で電子メールまたはメッセージを送信する必要があります regUser.getCode();
            return ResponseEntity.status(Code.OK).body(new AuthenticationResponse(Code.REGISTER_CODE, "You must use reg link", null));
        } else {
            AuthUserEntity authUserEntity = StringUtils.authUserEntityGenerate(user);
            authUserRepository.save(authUserEntity);

            return ResponseEntity.status(Code.OK).body(new AuthenticationResponse(Code.AUTH_CODE, "You must use auth link", null));
        }

    }

    public ResponseEntity<AuthenticationResponse> check(String phoneNumber, Integer code) {
        var phone = StringUtils.formateCellPhone(phoneNumber);

        if (phone == null) {
            return ResponseEntity.status(Code.INVALID_DATA).body(new AuthenticationResponse(Code.INVALID_DATA, "Phone number is not validate", null));
        }
        User user = userRepository.getUserByPhone(phone);
        if (user == null) {
            return ResponseEntity.status(Code.UNKNOWN_ERROR).body(new AuthenticationResponse(Code.UNKNOWN_ERROR, "Unknown error!", null));

        } else {
            AuthUserEntity authUserEntity = authUserRepository.getAuthUserEntitiesByCodeAndUser(code, user);
            if (authUserEntity != null) {
                authUserRepository.delete(authUserEntity);
                //then you should send user on authentication and get for him token
                String token = authenticationUser(user);


                AuthToken authToken = StringUtils.createAuthToken(user,token);
                tokenRepository.save(authToken);


                return ResponseEntity.status(Code.OK).body(new AuthenticationResponse(Code.OK, "OK", token));
            } else {
                return ResponseEntity.status(Code.CODE_NOT_VALID).body(new AuthenticationResponse(Code.CODE_NOT_VALID, "CODE NOT VALID", null));
            }
        }
    }


    public ResponseEntity<AuthenticationResponse> regcheck(String phoneNumber, Integer code) {
        var phone = StringUtils.formateCellPhone(phoneNumber);

        if (phone == null) {
            return ResponseEntity.status(Code.INVALID_DATA).body(new AuthenticationResponse(Code.INVALID_DATA, "Phone number is not validate", null));
        }

        RegUserEntity regUserEntity = regUserRepository.getRegUserEntitiesByPhoneAndCode(phone, code);
        if (regUserEntity == null) {
            return ResponseEntity.status(Code.CODE_NOT_VALID).body(new AuthenticationResponse(Code.CODE_NOT_VALID, "CODE NOT VALID", null));
        } else {
            User user = StringUtils.generateUser(phone, Role.USER);
            userRepository.save(user);

            //here user authenticating with generate for him token
            String token = authenticationUser(user);


            AuthToken authToken = StringUtils.createAuthToken(user,token);
            tokenRepository.save(authToken);

            return ResponseEntity.status(Code.OK).body(new AuthenticationResponse(Code.OK, "OK", token));
        }
    }


    public String authenticationUser(User user){
        return this.jwtService.generateToken(user);
    }

}
