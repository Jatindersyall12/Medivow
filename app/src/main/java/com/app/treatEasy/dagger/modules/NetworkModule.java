package com.app.treatEasy.dagger.modules;

import android.content.Context;

import java.util.concurrent.TimeUnit;

import com.app.treatEasy.BuildConfig;
import com.app.treatEasy.dagger.scopes.AppScope;
import com.app.treatEasy.networking.AuthenticationInterceptor;
import com.app.treatEasy.networking.RxErrorHandlingCallAdapterFactory2;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
/*Created by Vinod Kumar (Aug 2019)*/

@Module
public class NetworkModule {

    @Provides
    @AppScope
    OkHttpClient provideOkHttpClientInstance(Context context) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS);
        builder.addInterceptor(new AuthenticationInterceptor(context));
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(interceptor);
        return builder.build();
    }

    @Provides
    @AppScope
    Retrofit provideRetrofitInstance(OkHttpClient okHttpClient) {
        Retrofit.Builder builder = new Retrofit.Builder().
                client(okHttpClient).
                addConverterFactory(GsonConverterFactory.create()).
                addCallAdapterFactory(RxErrorHandlingCallAdapterFactory2.create())
                .baseUrl(BuildConfig.BaseUrl);

        return builder.build();
    }

}