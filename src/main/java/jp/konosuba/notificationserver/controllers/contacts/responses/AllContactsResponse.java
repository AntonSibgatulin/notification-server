package jp.konosuba.notificationserver.controllers.contacts.responses;

import jp.konosuba.notificationserver.data.contact.Contacts;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllContactsResponse {
    private List<Contacts> lists = new ArrayList<>();
}
