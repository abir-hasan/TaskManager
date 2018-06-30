package com.example.abirhasan.finaltest.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.text.SimpleDateFormat;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;

public class AppUtils {

    public static String getUser(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(Constants.MY_PREFS_NAME, MODE_PRIVATE);
        return prefs.getString(Constants.USER_NAME_KEY, null);
    }

    public static SimpleDateFormat getFormat() {
        return new SimpleDateFormat("dd-MMM-yyyy",
                Locale.getDefault());

    }

    public static String getFormattedDate(long dueDate) {
        SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy",
                Locale.getDefault());
        return format.format(dueDate);

    }
}
