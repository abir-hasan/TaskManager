package com.example.abirhasan.finaltest.dagger.rest;

import android.content.Context;

import dagger.Module;
import dagger.Provides;


@Module
public class ContextModule {

    private final Context context;

    public ContextModule(Context context) {
        this.context = context;
    }

    @Provides
    @EndPointScope
    public Context context() {
        return context;
    }

}
