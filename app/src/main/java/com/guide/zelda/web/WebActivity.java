package com.guide.zelda.web;

import android.content.Context;
import android.content.Intent;

import com.guide.zelda.R;
import com.guide.zelda.base.BaseActivity;
import com.guide.zelda.di.component.ApplicationComponent;


public class WebActivity extends BaseActivity {

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_web;
    }

    @Override
    protected void setupActivityComponent(ApplicationComponent component) {

    }

    @Override
    protected void initView() {
        String url = getIntent().getStringExtra("url");
        loadRootFragment(R.id.web_container, WebViewFragment.newInstance(url));
    }

    @Override
    protected void init() {

    }

    public static void startWebFragment(Context context, String url) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }

}