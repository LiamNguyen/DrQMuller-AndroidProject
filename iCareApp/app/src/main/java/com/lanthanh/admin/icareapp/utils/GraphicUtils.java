package com.lanthanh.admin.icareapp.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by ADMIN on 01-Feb-17.
 */

public class GraphicUtils {
    public static final String FONT_LIGHT = "fonts/OpenSans-Light.ttf";
    public static final String FONT_REGULAR = "fonts/OpenSans-Regular.ttf";
    public static final String FONT_SEMIBOLD = "fonts/OpenSans-Semibold.ttf";
    public static final String FONT_BOLD = "fonts/OpenSans-Bold.ttf";
    public static final String FONT_WELCOME = "fonts/SourceSansPro-Light.ttf";

    public static int getScreenSizeWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        return metrics.widthPixels;
    }

    public static int getScreenSizeHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        return metrics.heightPixels;
    }
}
