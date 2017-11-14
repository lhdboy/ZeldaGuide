package com.guide.zelda.home;

import com.guide.zelda.base.MvpView;

import me.yokeyword.fragmentation.SupportFragment;


public interface MainView extends MvpView {

    void startFragment(SupportFragment fragment);

}