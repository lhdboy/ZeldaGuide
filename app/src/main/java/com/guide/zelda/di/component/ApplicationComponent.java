package com.guide.zelda.di.component;

import android.content.Context;

import com.guide.zelda.AppApplication;
import com.guide.zelda.common.WeakHandler;
import com.guide.zelda.common.bus.RxEventBus;
import com.guide.zelda.common.rxjava.SingletonRxServiceExecutor;
import com.guide.zelda.common.utils.PreferenceFactory;
import com.guide.zelda.di.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(AppApplication appApplication);

    Context context();

    PreferenceFactory preferenceFactory();

    SingletonRxServiceExecutor singletonRxServiceExcutor();

    WeakHandler weakHandler();

    RxEventBus rxEventBus();

}