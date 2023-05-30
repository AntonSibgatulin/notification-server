package jp.konosuba.notificationserver.controllers.auth.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class AuthenticationResponse {

    private Integer code;
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String token;

}
