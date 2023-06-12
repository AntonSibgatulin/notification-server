package jp.konosuba.notificationserver.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
@RequestMapping("/api/v1/auth_token_check")
@RestController
public class MainController {
    @GetMapping("/")
    public ResponseEntity<ResponseMain> checkToken(){
        return ResponseEntity.ok(new ResponseMain());
    }

    class ResponseMain{
        public String code="OK";
    }

}
