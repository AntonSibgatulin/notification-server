package jp.konosuba.notificationserver.data.contact;

import jp.konosuba.notificationserver.controllers.contacts.util.ContactModel;

public interface ContactsMapper {
    Contacts fromContactModeltoContacts(ContactModel contactModel);

}
