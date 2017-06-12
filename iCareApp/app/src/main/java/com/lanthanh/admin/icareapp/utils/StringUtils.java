package com.lanthanh.admin.icareapp.utils;

import com.lanthanh.admin.icareapp.presentation.model.dto.DTOAppointment;

/**
 * @author longv
 *         Created on 10-Apr-17.
 */

public class StringUtils {
    /**
     * Check whether the input CharSequence is not empty
     * @param s The input CharSequence
     * @return true if the input CharSequence is not empty or false if the input CharSequence is empty or null
     */
    public static boolean isNotEmpty(CharSequence s) {
        return s != null && !s.toString().isEmpty();
    }

    @SafeVarargs
    public static <S extends CharSequence> boolean isNotEmpty(S... charArray) {
        if (charArray == null)
            return false;
        for (int i = 0; i < charArray.length; i++) {
            if (charArray[i] == null || charArray[i].toString().isEmpty())
                return false;
        }
        return true;
    }

    public static <S extends CharSequence> boolean isNotNull(S s) {
        return s != null;
    }

    public static <S extends CharSequence> boolean validatePattern(S s, String pattern) {
        return s != null && s.toString().matches(pattern);
    }

    public static <S1 extends CharSequence, S2 extends CharSequence> boolean isEqual(S1 s1, S2 s2) {
        return s1 != null && s2 != null && s1.toString().equals(s2.toString());
    }

    public static String formFullAddress(DTOAppointment appointment) {
        return String.format(
                "%s, %s, %s, %s",
                appointment.getLocation().getAddress(),
                appointment.getDistrict().getDistrictName(),
                appointment.getCity().getCityName(),
                appointment.getCountry().getCountryName());
    }
}
