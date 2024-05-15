package com.imooc.mall.util;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EmailUtils {

    public static boolean isValidEmailAddress(String email) {
        boolean result = true;
        try {
            InternetAddress address = new InternetAddress(email);
            address.validate();
        } catch (AddressException e) {
            e.printStackTrace();
            result = false;
        }
        return result;
    }

    public static String genVerificationCode() {
        List<String> verificationChars = Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
                "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z",
                "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z");
        Collections.shuffle(verificationChars);
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            result.append(verificationChars.get(i));
        }
        return result.toString();
    }
}
