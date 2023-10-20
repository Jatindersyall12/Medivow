package com.app.treatEasy.networking;


import android.content.Context;

import com.app.treatEasy.BaseAppApplication;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
/*Created by Vinod Kumar (Aug 2019)*/

/*This class is used to intercept the request created by retrofit and add required parameter for network bidding... */
public class AuthenticationInterceptor implements Interceptor {

    private static final String KEY_AUTHORIZATION = "Authorization";

    public AuthenticationInterceptor(Context context) {
        BaseAppApplication.getApp().getDaggerAppComponent().provideIn((BaseAppApplication) context.getApplicationContext());
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request originalRequest = chain.request();
        Request.Builder builder = originalRequest.newBuilder();
        builder.header("Content-Type", "application/json");
        builder.header("Authorization", "sadas21321");

        Request modifiedRequest = builder.build();
        return chain.proceed(modifiedRequest);
    }
}
