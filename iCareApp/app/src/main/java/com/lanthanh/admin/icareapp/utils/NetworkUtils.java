package com.lanthanh.admin.icareapp.utils;

import java.security.SecureRandom;

/**
 * Created by ADMIN on 06-Jan-17.
 */

public class NetworkUtils {

    public static String generateVerificationCode() {
        return generateVerificationCode(8);
    }

    public static String generateVerificationCode(int length) {
        String AB = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        SecureRandom rnd = new SecureRandom();

        StringBuilder sb = new StringBuilder(length);
        for( int i = 0; i < length; i++ )
            sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
        return sb.toString();
    }
}
