package com.example.abirhasan.finaltest.utils;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class AppUtils {

    public static String getUser(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(Constants.MY_PREFS_NAME, MODE_PRIVATE);
        return prefs.getString(Constants.USER_NAME_KEY, null);
    }
}