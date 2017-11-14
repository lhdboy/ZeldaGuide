package com.guide.zelda.api;

import android.content.Context;
import android.text.TextUtils;

import com.guide.zelda.AppApplication;
import com.guide.zelda.common.Constant;
import com.guide.zelda.common.preference.UserPreference;
import com.guide.zelda.common.utils.PreferenceFactory;
import com.guide.zelda.di.component.ApplicationComponent;
import com.guide.zelda.di.component.DaggerInterceptorComponent;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class StockholmInterceptor implements Interceptor {

    @Inject
    PreferenceFactory preferenceFactory;

    @Inject
    public StockholmInterceptor(Context context) {
        ApplicationComponent component = ((AppApplication) context.getApplicationContext()).getApplicationComponent();
        DaggerInterceptorComponent.builder().applicationComponent(component).build().inject(this);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder()
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .addHeader("Platform", Constant.PLATFORM_ANDROID);

        UserPreference userPreference = preferenceFactory.create(UserPreference.class);
        if (!TextUtils.isEmpty(userPreference.getUserAccessToken())) {
            builder.addHeader("Access-Token", userPreference.getUserAccessToken());
        }
        String uuid = chain.request().header("UUID");
        if (TextUtils.isEmpty(uuid))
            if (!TextUtils.isEmpty(userPreference.getOperatingDeviceId())) {
                builder.addHeader("UUID", userPreference.getOperatingDeviceId());
            }
        return chain.proceed(builder.build());
    }

}
