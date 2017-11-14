package com.guide.zelda.di.component;


import com.guide.zelda.MainActivity;
import com.guide.zelda.di.Scopes;
import com.guide.zelda.di.module.ActivityModule;

import dagger.Component;

@Scopes.Activity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(MainActivity activity);

}