package jp.konosuba.notificationserver.utils;

import org.junit.jupiter.api.Test;

public class StringUtilsTest {


    @Test
    public void checkCellPhoneFormate(){
        System.out.println(StringUtils.formateCellPhone("79999999999"));
    }
}
