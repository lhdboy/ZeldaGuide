package com.guide.zelda.home;

import com.guide.zelda.base.MvpView;
import com.guide.zelda.db.model.GuideModel;

import java.util.List;


public interface GuideView extends MvpView {

    void getGuideListSuccess(List<GuideModel> list);

}