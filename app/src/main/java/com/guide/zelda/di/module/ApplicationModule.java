package com.guide.zelda.di.module;

import android.app.Application;
import android.content.Context;

import com.guide.zelda.AppApplication;
import com.guide.zelda.common.WeakHandler;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(includes = ApiModule.class)
public class ApplicationModule {
    private Application application;

    public ApplicationModule(AppApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return this.application;
    }

    @Provides
    @Singleton
    public Application provideApplication() {
        return application;
    }

    @Provides
    @Singleton
    public WeakHandler provideWeakHandler() {
        return new WeakHandler();
    }

}