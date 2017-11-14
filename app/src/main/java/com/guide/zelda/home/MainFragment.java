package com.guide.zelda.home;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.View;

import com.guide.zelda.R;
import com.guide.zelda.base.BaseFragment;
import com.guide.zelda.di.component.ApplicationComponent;
import com.guide.zelda.di.component.DaggerFragmentComponent;
import com.guide.zelda.widget.TitleView;
import com.guide.zelda.widget.WebViewFragment;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import javax.inject.Inject;

import butterknife.BindView;
import me.yokeyword.fragmentation.SupportFragment;
import rx.Observable;


public class MainFragment extends BaseFragment implements MainView {

    @BindView(R.id.indicator_main)
    MagicIndicator magicIndicator;
    @BindView(R.id.view_pager)
    ViewPager viewPager;

    public static final int TAB_SETTING = 0;
    public static final int TAB_STORE = 1;
    public static final int TAB_PROFILE = 2;
    public static final int TAB_COUNT = 3;

    @Inject
    MainPresenter presenter;
    private BaseFragment[] fragments = new BaseFragment[TAB_COUNT];

    private int[] titleTextData = new int[]{R.string.main_tab_whole_flow, R.string.main_tab_newest, R.string.main_tab_map};

    public static MainFragment newInstance() {
        Bundle args = new Bundle();
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    protected void setupFragmentComponent(ApplicationComponent component) {
        DaggerFragmentComponent.builder().applicationComponent(component).build().inject(this);
    }

    @Override
    protected void initView(TitleView titleView, Bundle savedInstanceState) {
        presenter.attachView(this);
        titleView.setVisibility(View.GONE);
        StoreAdapter adapter = new StoreAdapter(getActivity().getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        initIndicator();
    }

    @Override
    protected void init() {
        presenter.init();
    }

    private void initIndicator() {
        CommonNavigator commonNavigator = new CommonNavigator(context);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return titleTextData.length;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ColorTransitionPagerTitleView(context);
                simplePagerTitleView.setSelectedColor(getResources().getColor(R.color.colorBlack));
                simplePagerTitleView.setNormalColor(getResources().getColor(R.color.colorBlackLight));
                simplePagerTitleView.setText(titleTextData[index]);
                simplePagerTitleView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                simplePagerTitleView.setOnClickListener(v -> viewPager.setCurrentItem(index));
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator linePagerIndicator = new LinePagerIndicator(context);
                linePagerIndicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
                linePagerIndicator.setColors(getResources().getColor(R.color.colorBlack));
                return linePagerIndicator;
            }
        });
        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator, viewPager);
    }

    @Override
    public void startFragment(SupportFragment fragment) {
        start(fragment);
    }

    @Override
    public <T> Observable.Transformer<T, T> bindLifecycle() {
        return this.bindToLifecycle();
    }

    @Override
    public void showMsg(String msg) {

    }

    private class StoreAdapter extends FragmentStatePagerAdapter {

        public StoreAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public BaseFragment getItem(int position) {
            if (position == 0) {
                return WholeFlowFragment.newInstance();
            } else if (position == 1){
                return NewestFragment.newInstance();
            } else  {
                return MapFragment.newInstance();
            }
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}