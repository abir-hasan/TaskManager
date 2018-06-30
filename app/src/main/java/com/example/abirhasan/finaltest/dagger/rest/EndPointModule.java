package com.example.abirhasan.finaltest.dagger.rest;

import com.example.abirhasan.finaltest.network.EndPoints;
import com.google.gson.Gson;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


@Module(includes = {NetworkModule.class, GsonModule.class})
public class EndPointModule {

    @Provides
    @EndPointScope
    public EndPoints tmdbService(Retrofit retrofit) {
        return retrofit.create(EndPoints.class);
    }

    @Provides
    @EndPointScope
    public Retrofit retrofit(OkHttpClient okHttpClient, Gson gson) {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .baseUrl(EndPoints.BASE_URL)
                .build();
    }
}
