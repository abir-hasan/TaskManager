package com.example.abirhasan.finaltest;

import android.app.Activity;
import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

public class BaseApp extends Application {
    private static final String TAG = "BaseApp";

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }

    public static BaseApp get(Activity activity) {
        return (BaseApp) activity.getApplication();
    }

}
