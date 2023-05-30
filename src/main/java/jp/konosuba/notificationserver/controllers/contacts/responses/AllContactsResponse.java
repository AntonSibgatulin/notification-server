package jp.konosuba.notificationserver.controllers.contacts.responses;

import jp.konosuba.notificationserver.contact.Contacts;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class AllContactsResponse {
    private List<Contacts> lists;
}
