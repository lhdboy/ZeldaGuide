package com.guide.zelda.home;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.guide.zelda.R;
import com.guide.zelda.base.BaseFragment;
import com.guide.zelda.di.component.ApplicationComponent;
import com.guide.zelda.widget.TitleView;
import com.just.library.AgentWeb;

import butterknife.BindView;


public class MapFragment extends BaseFragment {

    @BindView(R.id.layout_root)
    LinearLayout layoutRoot;

    private AgentWeb agentWeb;

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

    }

    @Override
    protected void initView(TitleView titleView, Bundle savedInstanceState) {
        titleView.setVisibility(View.GONE);
    }

    @Override
    protected void init() {
        agentWeb = AgentWeb.with(this)
                .setAgentWebParent(layoutRoot, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .createAgentWeb()
                .ready()
                .go("file:///android_asset/MapResource/Index.html");
        String js = "$(\"#TypeSwitch li[data-type='" + 1925 + "']\").click()";
        agentWeb.getJsEntraceAccess().quickCallJs("");
    }

    
}
