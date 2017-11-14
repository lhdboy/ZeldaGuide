package com.guide.zelda.di.component;


import com.guide.zelda.api.StockholmInterceptor;
import com.guide.zelda.di.Scopes;
import com.guide.zelda.di.module.InterceptorModule;

import dagger.Component;

@Scopes.Interceptor
@Component(dependencies = ApplicationComponent.class, modules = {InterceptorModule.class})
public interface InterceptorComponent {

    void inject(StockholmInterceptor interceptor);

}