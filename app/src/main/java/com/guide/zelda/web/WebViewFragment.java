package com.guide.zelda.web;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.guide.zelda.R;
import com.guide.zelda.base.BaseFragment;
import com.guide.zelda.di.component.ApplicationComponent;
import com.guide.zelda.di.component.DaggerFragmentComponent;
import com.guide.zelda.widget.TitleView;
import com.just.library.AgentWeb;
import com.just.library.WebDefaultSettingsManager;

import butterknife.BindView;


public class WebViewFragment extends BaseFragment {

    @BindView(R.id.layout_web)
    LinearLayout layoutWeb;
    private AgentWeb agentWeb;

    public static WebViewFragment newInstance(String url) {
        Bundle args = new Bundle();
        args.putString("key_url", url);
        WebViewFragment fragment = new WebViewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_web;
    }

    @Override
    protected void setupFragmentComponent(ApplicationComponent component) {
        DaggerFragmentComponent.builder().applicationComponent(component).build().inject(this);
    }

    @Override
    protected void initView(TitleView titleView, Bundle savedInstanceState) {
        titleView.clickLeft(l -> {
            if (!agentWeb.back()) {
                getActivity().finish();
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