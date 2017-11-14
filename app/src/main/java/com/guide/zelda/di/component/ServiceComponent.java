package com.guide.zelda.di.component;


import com.guide.zelda.di.Scopes;
import com.guide.zelda.di.module.ServiceModule;

import dagger.Component;

@Scopes.Service
@Component(dependencies = ApplicationComponent.class, modules = ServiceModule.class)
public interface ServiceComponent {

}
