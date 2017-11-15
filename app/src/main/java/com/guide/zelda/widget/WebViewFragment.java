package com.guide.zelda.widget;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.guide.zelda.R;
import com.guide.zelda.base.BaseFragment;
import com.guide.zelda.di.component.ApplicationComponent;
import com.guide.zelda.di.component.DaggerFragmentComponent;
import com.just.library.AgentWeb;
import com.just.library.WebDefaultSettingsManager;

import butterknife.BindView;


public class WebViewFragment extends BaseFragment {

    @BindView(R.id.layout_web)
    LinearLayout layoutWeb;
    private AgentWeb agentWeb;

    public static WebViewFragment newInstance(String title, String url) {
        Bundle args = new Bundle();
        args.putString("key_title", title);
        args.putString("key_url", url);
        WebViewFragment fragment = new WebViewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_base_web;
    }

    @Override
    protected void setupFragmentComponent(ApplicationComponent component) {
        DaggerFragmentComponent.builder().applicationComponent(component).build().inject(this);
    }

    @Override
    protected void initView(TitleView titleView, Bundle savedInstanceState) {
        String title = getArguments().getString("key_title");
        if (TextUtils.isEmpty(title)) titleView.setVisibility(View.GONE);
        titleView.centerTitle(title);
        titleView.clickLeft(l -> {
            if (!agentWeb.back()) {
                pop();
            }
        });
    }

    @Override
    protected void init() {
        String url = getArguments().getString("key_url");
        agentWeb = AgentWeb.with(this)
                .setAgentWebParent(layoutWeb, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .setAgentWebWebSettings(WebDefaultSettingsManager.getInstance())
                .setReceivedTitleCallback((view, title) -> titleView.centerTitle(title))
                .createAgentWeb()
                .ready()
                .go(url);
    }

    @Override
    public void onResume() {
        if (agentWeb != null)
            agentWeb.getWebLifeCycle().onResume();
        super.onResume();
    }

    @Override
    public void onPause() {
        if (agentWeb != null)
            agentWeb.getWebLifeCycle().onPause();
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        if (agentWeb != null)
            agentWeb.getWebLifeCycle().onDestroy();
        super.onDestroyView();
    }

}