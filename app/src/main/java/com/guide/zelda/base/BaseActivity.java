package com.guide.zelda.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;

import com.guide.zelda.AppApplication;
import com.guide.zelda.di.component.ApplicationComponent;
import com.jaeger.library.StatusBarUtil;

import butterknife.ButterKnife;


public abstract class BaseActivity extends RxLifecycleActivity {

    @LayoutRes
    protected abstract int getContentLayoutId();

    protected abstract void setupActivityComponent(ApplicationComponent component);

    protected abstract void initView();

    protected abstract void init();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getContentLayoutId() != 0) {
            setContentView(getContentLayoutId());
        }
        ButterKnife.bind(this);
        setupActivityComponent(((AppApplication) getApplication()).getApplicationComponent());
        initView();
        init();
        StatusBarUtil.setTranslucent(this, 70);
    }

}