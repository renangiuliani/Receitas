package com.example.renan.testepls.Util;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.util.Locale;

/**
 * Created by c1284141 on 29/09/2015.
 */
public class Util {

    public static final Locale LOCALE_PT_BR = new Locale("pt", "BR");
    private static final String PATTERN_TIME = "HH:mm";

    public static Context CONTEXT;

    public static void hideShowKeyboard(Context context, View editText) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

//    public static String formatTime(Date date) {
//        return format(date, Util.PATTERN_TIME);
//    }
}
