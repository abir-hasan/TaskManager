package com.example.abirhasan.finaltest;

import android.app.Activity;
import android.app.Application;

import com.example.abirhasan.finaltest.dagger.rest.ContextModule;
import com.example.abirhasan.finaltest.dagger.rest.DaggerEndPointComponent;
import com.example.abirhasan.finaltest.dagger.rest.EndPointComponent;
import com.google.firebase.database.FirebaseDatabase;

public class BaseApp extends Application {
    private static final String TAG = "BaseApp";
    private EndPointComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        /*component = DaggerEndPointComponent.builder()
                .contextModule(new ContextModule(this))
                .build();*/


    }

    public EndPointComponent tmDbServiceComponent() {
        return this.component;
    }

    public static BaseApp get(Activity activity) {
        return (BaseApp) activity.getApplication();
    }

}
