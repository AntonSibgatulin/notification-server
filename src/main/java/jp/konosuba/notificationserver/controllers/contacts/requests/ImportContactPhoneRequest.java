package jp.konosuba.notificationserver.controllers.contacts.requests;

import jp.konosuba.notificationserver.controllers.contacts.util.ContactModel;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ImportContactPhoneRequest {
    private List<ContactModel> contacts = new ArrayList<>();
}
