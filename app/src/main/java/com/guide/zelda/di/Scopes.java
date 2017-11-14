package com.guide.zelda.di;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

public interface Scopes {

    @Documented
    @Scope
    @Retention(RetentionPolicy.RUNTIME)
    @interface Activity {
    }

    @Documented
    @Scope
    @Retention(RetentionPolicy.RUNTIME)
    @interface Receiver {
    }

    @Documented
    @Scope
    @Retention(RetentionPolicy.RUNTIME)
    @interface Fragment {
    }

    @Documented
    @Scope
    @Retention(RetentionPolicy.RUNTIME)
    @interface Interceptor {
    }

    @Documented
    @Scope
    @Retention(RetentionPolicy.RUNTIME)
    @interface Service {
    }
}
