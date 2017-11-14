package com.guide.zelda.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.guide.zelda.AppApplication;
import com.guide.zelda.R;
import com.guide.zelda.common.bus.RxEventBus;
import com.guide.zelda.di.component.ApplicationComponent;
import com.guide.zelda.event.StartBrotherEvent;
import com.guide.zelda.widget.TitleView;

import javax.inject.Inject;

import butterknife.ButterKnife;

public abstract class BaseFragment extends RxLifecycleFragment {

    protected Activity activity;

    protected Context context;

    protected TitleView titleView;

    @Inject
    RxEventBus eventBus;

    @LayoutRes
    protected abstract int getContentLayoutId();

    protected abstract void setupFragmentComponent(ApplicationComponent component);

    protected abstract void initView(TitleView titleView, Bundle savedInstanceState);

    protected abstract void init();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (Activity) context;
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View baseView = inflater.inflate(R.layout.fragment_base, container, false);
        ViewGroup baseLayout = (ViewGroup) baseView.findViewById(R.id.layout_base);
        titleView = (TitleView) baseView.findViewById(R.id.titleView);

        View childView = inflater.inflate(getContentLayoutId(), null, false);
        ButterKnife.bind(this, childView);
        baseLayout.addView(childView);

        setupFragmentComponent(((AppApplication) activity.getApplication()).getApplicationComponent());
        initView(titleView, savedInstanceState);
        return baseView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    protected void doBack() {
        activity.onBackPressed();
    }

    public void startFragment(BaseFragment fragment) {
        eventBus.post(new StartBrotherEvent(fragment));
    }

}