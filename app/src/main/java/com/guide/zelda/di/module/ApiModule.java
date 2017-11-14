package com.guide.zelda.di.module;

import android.content.Context;

import com.guide.zelda.api.BaseUrl;
import com.guide.zelda.api.EnvProvider;
import com.guide.zelda.api.ServiceFactory;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApiModule {

    @Provides
    @Singleton
    @Named("com.adam.api")
    public BaseUrl provideBaseUrl(EnvProvider provider) {
        return new BaseUrl(provider.get().getApiUrl());
    }

    @Provides
    @Singleton
    @Named("adam.api.service.factory")
    public ServiceFactory provideApiServiceFactory(Context context, @Named("com.adam.api") BaseUrl baseUrl) {
        return new ServiceFactory(context, baseUrl);
    }

//    @Provides
//    @Singleton
//    public SettingService provideSettingService(@Named("adam.api.service.factory") ServiceFactory factory) {
//        return factory.create(SettingService.class);
//    }

}