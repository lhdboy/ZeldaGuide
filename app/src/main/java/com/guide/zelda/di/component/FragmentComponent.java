package com.guide.zelda.di.component;


import com.guide.zelda.map.MapFragment;
import com.guide.zelda.home.NewestFragment;
import com.guide.zelda.home.WholeFlowFragment;
import com.guide.zelda.widget.WebViewFragment;
import com.guide.zelda.di.Scopes;
import com.guide.zelda.di.module.FragmentModule;
import com.guide.zelda.home.MainFragment;

import dagger.Component;

@Scopes.Fragment
@Component(dependencies = ApplicationComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {

    void inject(MainFragment fragment);

    void inject(WebViewFragment fragment);

    void inject(WholeFlowFragment flowFragment);

    void inject(NewestFragment fragment);

    void inject(MapFragment fragment);

}