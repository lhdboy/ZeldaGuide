package com.guide.zelda.home;

import com.guide.zelda.base.BasePresenter;
import com.guide.zelda.common.bus.RxEventBus;
import com.guide.zelda.event.StartBrotherEvent;

import javax.inject.Inject;


public class MainPresenter extends BasePresenter<MainView> {

    private RxEventBus eventBus;

    @Inject
    public MainPresenter(RxEventBus eventBus) {
        this.eventBus = eventBus;
    }

    public void init() {
        eventBus.subscribe(StartBrotherEvent.class, event -> {
            getMvpView().startFragment(event.targetFragment);
        });
    }

    public void unRegisterEventBus() {
        eventBus.unsubscribe();
    }

}