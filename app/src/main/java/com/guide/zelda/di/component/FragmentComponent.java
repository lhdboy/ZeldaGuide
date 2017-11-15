package com.guide.zelda.di.component;


import com.guide.zelda.home.UserShareFragment;
import com.guide.zelda.map.MapFragment;
import com.guide.zelda.home.MainQuestFragment;
import com.guide.zelda.web.WebViewFragment;
import com.guide.zelda.di.Scopes;
import com.guide.zelda.di.module.FragmentModule;
import com.guide.zelda.home.MainFragment;

import dagger.Component;

@Scopes.Fragment
@Component(dependencies = ApplicationComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {

    void inject(MainFragment fragment);

    void inject(WebViewFragment fragment);

    void inject(MainQuestFragment flowFragment);

    void inject(UserShareFragment fragment);

    void inject(MapFragment fragment);

}