package com.guide.zelda.home;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.guide.zelda.R;
import com.guide.zelda.base.BaseFragment;
import com.guide.zelda.db.model.GuideModel;
import com.guide.zelda.di.component.ApplicationComponent;
import com.guide.zelda.di.component.DaggerFragmentComponent;
import com.guide.zelda.web.WebActivity;
import com.guide.zelda.widget.TitleView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import rx.Observable;


public class MainQuestFragment extends BaseFragment implements GuideView {

    @BindView(R.id.recycler)
    RecyclerView recyclerView;

    @Inject
    GuidePresenter presenter;

    private GuideAdapter guideAdapter;

    public static MainQuestFragment newInstance() {
        Bundle args = new Bundle();
        MainQuestFragment fragment = new MainQuestFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_whole_flow;
    }

    @Override
    protected void setupFragmentComponent(ApplicationComponent component) {
        DaggerFragmentComponent.builder().applicationComponent(component).build().inject(this);
    }

    @Override
    protected void initView(TitleView titleView, Bundle savedInstanceState) {
        titleView.setVisibility(View.GONE);
        presenter.attachView(this);
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);

        guideAdapter = new GuideAdapter(context, null);
        recyclerView.setAdapter(guideAdapter);
        recyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                GuideModel model = (GuideModel) adapter.getItem(position);
                WebActivity.startWebFragment(context, "file:///android_asset/Guide/" + model.file_name);
            }
        });
    }

    @Override
    protected void init() {
        presenter.loadWholeFlowData();
    }

    @Override
    public void getGuideListSuccess(List<GuideModel> list) {
        guideAdapter.setNewData(list);
    }

    @Override
    public <T> Observable.Transformer<T, T> bindLifecycle() {
        return this.bindToLifecycle();
    }

    @Override
    public void showMsg(String msg) {

    }

}
