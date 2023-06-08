package jp.konosuba.notificationserver.utils;

import jp.konosuba.notificationserver.controllers.messages.requests.MessageRequest;
import jp.konosuba.notificationserver.data.messages.MessageEntity;
import jp.konosuba.notificationserver.data.messages.MessageObject;
import jp.konosuba.notificationserver.data.user.authuser.AuthUserEntity;
import jp.konosuba.notificationserver.data.user.reguser.RegUserEntity;
import jp.konosuba.notificationserver.data.user.token.AuthToken;
import jp.konosuba.notificationserver.data.user.user.Role;
import jp.konosuba.notificationserver.data.user.user.User;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.swing.text.MaskFormatter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.Base64;
import java.util.Random;

public class StringUtils {

    static String [] data = {"%","$","@","*","!","(","化","な","慣"};


    public static String generateCodeStatic(int length){
        String [] keys = {"Q","W","E","R","T","Y","U","I","O","P","A"
                ,"A","S","D","F","G","H","J","q","w","e","r","t","y","u","i","$","_",".","#","@"};
        String string = "";
        for (var i =0;i<length;i++){
            string+=keys[new Random().nextInt(keys.length)];
        }
        return string;

    }
    public static String formateCellPhone(String phone){

        var phoneMask= "+# (###) ###-##-##";
        var phoneNumber= phone.replaceAll("/+","");
        if (phoneNumber.startsWith("8")){
            phoneNumber = phoneNumber.replaceFirst("8","7");
        }

        MaskFormatter maskFormatter= null;
        try {
            maskFormatter = new MaskFormatter(phoneMask);
        } catch (ParseException e) {
            System.out.println(e);
            return null;
        }
        maskFormatter.setValueContainsLiteralCharacters(false);
        try {
            return maskFormatter.valueToString(phoneNumber) ;
        } catch (ParseException e) {
           System.out.println(e);
           return null;
        }

    }

    public static RegUserEntity regUserEntityGenerate(String phone){
        phone = formateCellPhone(phone);
        if (phone == null){
            return null;
        }
        var regUser = new RegUserEntity();
        regUser.setCode(new Random().nextInt(100000,999999));
        regUser.setPhone(phone);
        return regUser;

    }

    public static AuthUserEntity authUserEntityGenerate(User user){


        var authUserEntity = new AuthUserEntity();
        authUserEntity.setCode(new Random().nextInt(100000,999999));
        authUserEntity.setUser(user);
        return authUserEntity;

    }

    public static User generateUser(String phone, Role role) {
        var user = new User();
        user.setRole(role);
        user.setPhone(phone);
        return user;
    }

    public static AuthToken createAuthToken(User user, String token) {
        var authToken = new AuthToken();
        authToken.setToken(token);
        authToken.setUser(user);
        return authToken;
    }

    private String generateToken() {
        var encodedBytes = Base64.getEncoder().encode((generateKeyWord()).getBytes());
        return new String(encodedBytes);
    }

    public String generateKeyWord(){



        String str =System.currentTimeMillis()+"";
        for (var x =0;x<50+new Random().nextInt(256);x++){
            str+=data[new Random().nextInt(data.length)];
        }
        str +=System.currentTimeMillis()+"";
        return str;
    }

    public static User getUser(){
        User user = (User) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        return user;
    }

    public static MessageEntity generateMessage(MessageRequest messageRequest){
        var message = new MessageEntity();
        message.setMessage(messageRequest.getMessage());
        message.setTimeCreate(System.currentTimeMillis());
        message.setTypeMessage(messageRequest.getType());
        return message;
    }

    private static final char[] hex = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

    public static String byteArray2Hex(byte[] bytes) {
        StringBuffer sb = new StringBuffer(bytes.length * 2);
        for(final byte b : bytes) {
            sb.append(hex[(b & 0xF0) >> 4]);
            sb.append(hex[b & 0x0F]);
        }
        return sb.toString();
    }

    public static String getStringFromSHA256(String stringToEncrypt)  {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        messageDigest.update(stringToEncrypt.getBytes());
        return byteArray2Hex(messageDigest.digest());
    }



    public static String getTypeOfMessage(Integer type){
        if (type == 1){
            return "Info";
        }
        else if(type == 2){
            return "Warning";
        }else if(type == 3){
            return "Alarm";
        }else{
            return "Notification";
        }
    }
    public static MessageObject fromMessageEntityToMessageObject(MessageEntity messageEntity){
        return new MessageObject(messageEntity.getId(),messageEntity.getMessage(),messageEntity.getTimeCreate(),messageEntity.getContacts(),messageEntity.getTypeMessage());

    }
}
