package jp.konosuba.notificationserver.controllers.contacts.util;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ContactModel {

    @NotNull
    private String name;
    private String phone;
    private String email;

    private Boolean relative = false;
    private Boolean tg = false;
    private Boolean vk = false;
    private Boolean ws = false; //whatsapp



}
