package com.example.renan.recipeapplication.Util;

import android.animation.Animator;
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

    public static void animationClickAcionBar(final View view) {
        view.animate().setDuration(150).scaleX(70).scaleY(70).alpha((float) 0.1).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                view.setScaleX(1);
                view.setScaleY(1);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                view.setAlpha(0);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        }).start();
    }
}
