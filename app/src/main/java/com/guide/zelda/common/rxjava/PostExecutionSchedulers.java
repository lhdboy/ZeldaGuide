package com.guide.zelda.common.rxjava;

import rx.Scheduler;

public interface PostExecutionSchedulers {

    Scheduler getObserveScheduler();

    Scheduler getSubscribeScheduler();
}
