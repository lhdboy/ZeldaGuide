package com.guide.zelda;

import android.app.Application;

import com.blankj.utilcode.util.Utils;
import com.guide.zelda.common.LogUtil;
import com.guide.zelda.db.GuideRepository;
import com.guide.zelda.di.component.ApplicationComponent;
import com.guide.zelda.di.component.DaggerApplicationComponent;
import com.guide.zelda.di.module.ApiModule;
import com.guide.zelda.di.module.ApplicationModule;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

import javax.inject.Inject;

public class AppApplication extends Application {

    @Inject
    GuideRepository guideRepository;

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init() {
        FlowManager.init(new FlowConfig.Builder(this).build());
        LogUtil.init();
        initializeInjector();
        Utils.init(this);
        guideRepository.copyDB2Storage(this);
    }

    public ApplicationComponent getApplicationComponent() {
        return this.applicationComponent;
    }

    private void initializeInjector() {
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .apiModule(new ApiModule())
                .build();
        applicationComponent.inject(this);
    }

}