package jp.konosuba.notificationserver.utils;

import jp.konosuba.notificationserver.user.authuser.AuthUserEntity;
import jp.konosuba.notificationserver.user.reguser.RegUserEntity;
import jp.konosuba.notificationserver.user.user.User;

import javax.swing.text.MaskFormatter;
import java.text.ParseException;
import java.util.Random;

public class StringUtils {
    public static String generateCode(int length){
        String [] keys = {"Q","W","E","R","T","Y","U","I","O","P","A"
                ,"A","S","D","F","G","H","J","q","w","e","r","t","y","u","i","$","_",".","#","@"};
        String string = "";
        for (int i =0;i<length;i++){
            string+=keys[new Random().nextInt(keys.length)];
        }
        return string;

    }

    public static String generateCodeStatic(int length){
        String [] keys = {"Q","W","E","R","T","Y","U","I","O","P","A"
                ,"A","S","D","F","G","H","J","q","w","e","r","t","y","u","i","$","_",".","#","@"};
        String string = "";
        for (int i =0;i<length;i++){
            string+=keys[new Random().nextInt(keys.length)];
        }
        return string;

    }
    public static String formateCellPhone(String phone){

        String phoneMask= "+# (###) ###-##-##";
        String phoneNumber= phone.replaceAll("/+","");
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
        RegUserEntity regUser = new RegUserEntity();
        regUser.setCode(new Random().nextInt(100000,999999));
        regUser.setPhone(phone);
        return regUser;

    }

    public static AuthUserEntity authUserEntityGenerate(User user){


        AuthUserEntity authUserEntity = new AuthUserEntity();
        authUserEntity.setCode(new Random().nextInt(100000,999999));
        authUserEntity.setUser(user);
        return authUserEntity;

    }
}
