package com.guide.zelda.widget;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebSettings;

import com.guide.zelda.R;
import com.guide.zelda.base.BaseFragment;
import com.guide.zelda.di.component.ApplicationComponent;
import com.guide.zelda.di.component.DaggerFragmentComponent;

import butterknife.BindView;


public class WebViewFragment extends BaseFragment {

    @BindView(R.id.web_view_base)
    ProgressWebView webView;

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
        titleView.clickLeft(l -> doBack());
    }

    @Override
    protected void init() {
        String url = getArguments().getString("key_url");
        WebSettings settings = webView.getSettings();
        settings.setAppCacheEnabled(true);
        settings.setJavaScriptEnabled(true);
        webView.loadUrl(url);
    }

}