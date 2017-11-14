package com.guide.zelda.di.module;

import android.support.v4.app.Fragment;

import dagger.Module;
import dagger.Provides;


@Module()
public class FragmentModule {
    private Fragment fragment;

    public FragmentModule(Fragment fragment) {
        this.fragment = fragment;
    }

    @Provides
    public Fragment provideFragment() {
        return fragment;
    }
}

