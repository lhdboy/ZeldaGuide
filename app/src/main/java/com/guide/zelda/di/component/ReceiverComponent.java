package com.guide.zelda.di.component;


import com.guide.zelda.di.Scopes;
import com.guide.zelda.di.module.ReceiverModule;

import dagger.Component;

@Scopes.Receiver
@Component(dependencies = ApplicationComponent.class, modules = {ReceiverModule.class})
public interface ReceiverComponent {


}
