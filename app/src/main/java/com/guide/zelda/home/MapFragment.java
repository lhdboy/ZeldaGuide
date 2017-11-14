package com.guide.zelda.home;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.guide.zelda.R;
import com.guide.zelda.base.BaseFragment;
import com.guide.zelda.di.component.ApplicationComponent;
import com.guide.zelda.di.component.DaggerFragmentComponent;
import com.guide.zelda.map.MapPresenter;
import com.guide.zelda.map.MapView;
import com.guide.zelda.widget.TitleView;
import com.just.library.AgentWeb;
import com.just.library.WebDefaultSettingsManager;

import javax.inject.Inject;

import butterknife.BindView;
import rx.Observable;


public class MapFragment extends BaseFragment implements MapView {

    @BindView(R.id.layout_root)
    LinearLayout layoutRoot;

    @Inject
    MapPresenter presenter;

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
        presenter.attachView(this);
        titleView.clickLeft(l -> {
            if (!agentWeb.back()) {
                doBack();
            }
        });
    }

    @Override
    protected void init() {
        titleView.setVisibility(View.GONE);
//        String js = "$(\"#TypeSwitch li[data-type='" + 1926 + "']\").click()";
//        agentWeb.getJsEntraceAccess().quickCallJs(js);
    }

    @Override
    public void onResume() {
//        agentWeb.getWebLifeCycle().onResume();
        super.onResume();
        presenter.onResume();
    }

    @Override
    public void showDownloadDialog(boolean show) {
        if (show) {
            showDownloadDialog();
            agentWeb = AgentWeb.with(this)
                    .setAgentWebParent(layoutRoot, new LinearLayout.LayoutParams(-1, -1))
                    .useDefaultIndicator()
                    .setAgentWebWebSettings(WebDefaultSettingsManager.getInstance())
                    .setWebViewClient(mWebViewClient)
                    .setReceivedTitleCallback((view, title) -> titleView.centerTitle(title))
                    .createAgentWeb()
                    .ready()
                    .go("http://zelda.xisj.com/map/");
        } else {
            String url = context.getExternalFilesDir(null).getPath() + "/MapResource/Index.html";
            agentWeb = AgentWeb.with(this)
                    .setAgentWebParent(layoutRoot, new LinearLayout.LayoutParams(-1, -1))
                    .useDefaultIndicator()
                    .setAgentWebWebSettings(WebDefaultSettingsManager.getInstance())
                    .setWebViewClient(mWebViewClient)
                    .setReceivedTitleCallback((view, title) -> titleView.centerTitle(title))
                    .createAgentWeb()
                    .ready()
                    .go(url);
        }
    }

    private void showDownloadDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("提示").setMessage("未发现地图资源，下载后体验更佳。（Tips:需下载250Mb左右，请链接WiFi下载））")
                .setPositiveButton(R.string.confirm, (dialog, which) -> {
                    presenter.downloadMap();
                    dialog.dismiss();
                })
                .setNegativeButton(R.string.cancel, (dialog, which) -> dialog.dismiss());
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public <T> Observable.Transformer<T, T> bindLifecycle() {
        return this.bindToLifecycle();
    }

    @Override
    public void showMsg(String msg) {

    }

    @Override
    public void onPause() {
        agentWeb.getWebLifeCycle().onPause();
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        agentWeb.getWebLifeCycle().onDestroy();
        super.onDestroyView();
    }

}
