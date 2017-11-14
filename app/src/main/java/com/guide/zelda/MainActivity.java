package com.guide.zelda;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.guide.zelda.common.LogUtil;
import com.guide.zelda.di.component.ApplicationComponent;
import com.guide.zelda.di.component.DaggerActivityComponent;
import com.guide.zelda.home.MainFragment;

import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

public class MainActivity extends SupportActivity {
    private static final String TAG = MainFragment.class.getSimpleName();

    private FragmentManager.FragmentLifecycleCallbacks lifecycleCallbacks;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeInject();
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            loadRootFragment(R.id.fl_container, MainFragment.newInstance());
        }
        lifecycleCallbacks = new FragmentManager.FragmentLifecycleCallbacks() {
            @Override
            public void onFragmentResumed(FragmentManager fm, Fragment fragment) {
                super.onFragmentResumed(fm, fragment);
                LogUtil.i(TAG, "onFragmentSupportVisible:" + fragment.getClass().getSimpleName());
            }

            @Override
            public void onFragmentCreated(FragmentManager fm, Fragment f, Bundle savedInstanceState) {
                super.onFragmentCreated(fm, f, savedInstanceState);
            }
        };
        getSupportFragmentManager().registerFragmentLifecycleCallbacks(lifecycleCallbacks, true);
    }

    private void initializeInject() {
        ApplicationComponent component = ((AppApplication) getApplication()).getApplicationComponent();
        DaggerActivityComponent.builder().applicationComponent(component).build().inject(this);
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return new FragmentAnimator();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getSupportFragmentManager().unregisterFragmentLifecycleCallbacks(lifecycleCallbacks);
    }

}