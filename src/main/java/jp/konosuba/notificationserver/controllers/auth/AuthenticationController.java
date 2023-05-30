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

    // 私はサビスおしようします
    private final AuthenticationService authenticationService;


    @GetMapping("")
    public ResponseEntity<AuthenticationResponse> auth(@RequestParam String phone) {
        return authenticationService.auth(phone);
    }
    //for already registered users
    @GetMapping("/check")
    public ResponseEntity<AuthenticationResponse> check(@RequestParam String phone,@RequestParam Integer code) {
        return authenticationService.check(phone,code);
    }
    @GetMapping("/regcheck")
    public ResponseEntity<AuthenticationResponse> regcheck(@RequestParam String phone,@RequestParam Integer code) {
        return authenticationService.regcheck(phone,code);
    }


}
