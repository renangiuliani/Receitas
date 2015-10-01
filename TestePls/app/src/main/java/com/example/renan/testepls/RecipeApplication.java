package com.example.renan.testepls;

import android.app.Application;

import com.example.renan.testepls.Util.Util;

/**
 * Created by c1284141 on 01/10/2015.
 */
public class RecipeApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Util.CONTEXT = getApplicationContext();
    }

}
