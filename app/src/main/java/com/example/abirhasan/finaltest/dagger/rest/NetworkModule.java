package com.example.abirhasan.finaltest.dagger.rest;

import android.content.Context;

import java.io.File;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import timber.log.Timber;


@Module(includes = ContextModule.class)
public class NetworkModule {

    @Provides
    @EndPointScope
    public HttpLoggingInterceptor loggingInterceptor() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(
                new HttpLoggingInterceptor.Logger() {
                    @Override
                    public void log(String message) {
                        Timber.i(message);
                    }
                });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        return loggingInterceptor;
    }

    @Provides
    @EndPointScope
    public Cache cache(File cacheFile) {
        return new Cache(cacheFile, 10 * 1000 * 1000); //10MB
    }

    @Provides
    @EndPointScope
    public File file(Context context) {
        return new File(context.getCacheDir(), "okhttp_cache");
        //cacheFile.mkdirs();
    }


    @Provides
    @EndPointScope
    public OkHttpClient okHttpClient(HttpLoggingInterceptor loggingInterceptor, Cache cache) {
        return new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .cache(cache)
                .build();
    }
}
