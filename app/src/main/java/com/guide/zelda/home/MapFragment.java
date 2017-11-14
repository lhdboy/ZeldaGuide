package com.guide.zelda.home;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.guide.zelda.R;
import com.guide.zelda.base.BaseFragment;
import com.guide.zelda.di.component.ApplicationComponent;
import com.guide.zelda.di.component.DaggerFragmentComponent;
import com.guide.zelda.widget.TitleView;
import com.just.library.AgentWeb;
import com.just.library.WebDefaultSettingsManager;

import butterknife.BindView;


public class MapFragment extends BaseFragment {

    @BindView(R.id.layout_root)
    LinearLayout layoutRoot;

    private AgentWeb agentWeb;

    private WebViewClient mWebViewClient = new WebViewClient() {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            String str = request.getUrl().toString();
            if (str.startsWith("http")) {
//                MapFragment.this.mListener.onFragmentInteraction(Uri.parse(str));
            }
            return super.shouldOverrideUrlLoading(view, request);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return super.shouldOverrideUrlLoading(view, url);
        }
    };

    public static MapFragment newInstance() {
        Bundle args = new Bundle();
        MapFragment fragment = new MapFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_map;
    }

    @Override
    protected void setupFragmentComponent(ApplicationComponent component) {
        DaggerFragmentComponent.builder().applicationComponent(component).build().inject(this);
    }

    @Override
    protected void initView(TitleView titleView, Bundle savedInstanceState) {
        titleView.setVisibility(View.GONE);
        agentWeb = AgentWeb.with(this)
                .setAgentWebParent(layoutRoot, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .setAgentWebWebSettings(WebDefaultSettingsManager.getInstance())
                .setWebViewClient(mWebViewClient)
                .setReceivedTitleCallback((view, title) -> titleView.centerTitle(title))
                .createAgentWeb()
                .ready()
                .go("file:///android_asset/MapResource/Index.html");
    }

    @Override
    protected void init() {
        String js = "$(\"#TypeSwitch li[data-type='" + 1925 + "']\").click()";
//        agentWeb.getJsEntraceAccess().quickCallJs(js);
    }

    @Override
    public void onPause() {
        agentWeb.getWebLifeCycle().onPause();
        super.onPause();
    }

    @Override
    public void onResume() {
        agentWeb.getWebLifeCycle().onResume();
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        agentWeb.getWebLifeCycle().onDestroy();
        super.onDestroyView();
    }

}
