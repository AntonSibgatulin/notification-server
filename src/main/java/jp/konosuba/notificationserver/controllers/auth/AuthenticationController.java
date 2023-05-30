package jp.konosuba.notificationserver.controllers.auth;

import jp.konosuba.notificationserver.controllers.auth.responses.AuthenticationResponse;
import jp.konosuba.notificationserver.controllers.auth.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

private final AuthenticationService authenticationService ;

    


    @PostMapping("/")
    public ResponseEntity<AuthenticationResponse> auth(
            @RequestParam String phone
    ){
        // 私はサビスおしようします
        return authenticationService.auth(phone);
    }

}
