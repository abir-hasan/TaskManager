package com.example.abirhasan.finaltest.dagger.rest;

import com.fatboyindustrial.gsonjodatime.DateTimeConverter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.joda.time.DateTime;

import dagger.Module;
import dagger.Provides;


@Module
public class GsonModule {

    @Provides
    @EndPointScope
    public Gson gson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(DateTime.class, new DateTimeConverter());
        return gsonBuilder.create();
    }
}
