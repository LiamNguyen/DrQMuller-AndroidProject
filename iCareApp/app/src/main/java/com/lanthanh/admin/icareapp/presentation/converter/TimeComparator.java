package com.lanthanh.admin.icareapp.presentation.converter;

import java.util.Comparator;

/**
 * Created by ADMIN on 19-Nov-16.
 */

public class TimeComparator implements Comparator<String>{
    @Override
    public int compare(String time1, String time2) {

        int h1 = Integer.parseInt(time1.substring(0, time1.indexOf(":")));
        int m1 = Integer.parseInt(time1.substring(time1.indexOf(":")+1, time1.length()));
        int h2 = Integer.parseInt(time2.substring(0, time2.indexOf(":")));
        int m2 = Integer.parseInt(time2.substring(time2.indexOf(":")+1, time2.length()));

        if (h1 != h2)
            return h1 - h2;
        else
            return m1 - m2;
    }
}
