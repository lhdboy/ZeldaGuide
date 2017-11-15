package com.guide.zelda.map;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.guide.zelda.R;
import com.guide.zelda.base.BaseFragment;
import com.guide.zelda.di.component.ApplicationComponent;
import com.guide.zelda.di.component.DaggerFragmentComponent;
import com.guide.zelda.home.MainFragment;
import com.guide.zelda.widget.TitleView;
import com.just.library.AgentWeb;
import com.just.library.WebDefaultSettingsManager;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;


public class MapFragment extends BaseFragment implements MapView {

    private static final String TAG = MainFragment.class.getSimpleName();

    @BindView(R.id.layout_root)
    LinearLayout layoutRoot;
    @BindView(R.id.layout_progress)
    LinearLayout layoutProgress;
    @BindView(R.id.layout_map_tip)
    LinearLayout layoutMapTip;
    @BindView(R.id.tv_progress)
    TextView tvProgress;
    @BindView(R.id.pb_download)
    ProgressBar progressBar;
    @BindView(R.id.btn_download)
    Button btnDownload;

    @Inject
    MapPresenter presenter;

    private AgentWeb agentWeb;
    private boolean downloading;

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
        if (agentWeb != null)
            agentWeb.getWebLifeCycle().onResume();
        super.onResume();
        presenter.onResume();
    }

    @Override
    public void showDownloadTip(boolean show) {
        layoutMapTip.setVisibility(show ? View.VISIBLE : View.GONE);
        if (!show) {
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

    @Override
    public void updateProgress(String downloadSize, int progress) {
        layoutProgress.setVisibility(View.VISIBLE);
        tvProgress.setText(getString(R.string.download_progress, downloadSize, progress));
        progressBar.setProgress(progress);
    }

    @Override
    public void downloadPause() {
        btnDownload.setText(R.string.continue_download);
    }

    @Override
    public void downloadFinish(boolean success) {
        ToastUtils.showShort("downloadFinish");
        downloading = false;
    }

    @OnClick(R.id.btn_download)
    void download() {
        if (downloading) {
            presenter.pause();
            downloading = false;
        } else {
            downloading = true;
            presenter.downloadMap();
            btnDownload.setText(R.string.pause_download);
        }
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