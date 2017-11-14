package com.guide.zelda.home;


import com.guide.zelda.base.BasePresenter;
import com.guide.zelda.db.GuideRepository;
import com.guide.zelda.db.model.GuideModel;

import java.util.List;

import javax.inject.Inject;

public class GuidePresenter extends BasePresenter<GuideView> {

    private GuideRepository guideRepository;

    @Inject
    public GuidePresenter(GuideRepository guideRepository) {
        this.guideRepository = guideRepository;
    }

    void loadNewestData() {
        List<GuideModel> list = guideRepository.queryNewest();
        getMvpView().getGuideListSuccess(list);
    }

    void loadWholeFlowData() {
        List<GuideModel> list = guideRepository.queryWholeFlow();
        getMvpView().getGuideListSuccess(list);
    }

}