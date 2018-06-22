package com.xuyazhou.mynote.common.inject.models;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;


import dagger.Module;
import dagger.Provides;

/**
 * fragment上要用到的变量依赖声明
 *
 * Author: lampard_xu(xuyazhou18@gmail.com)
 * <p>
 * Date: 2016-03-03
 */
@Module
public class FragmentModule {

    final Fragment fragment;

    public FragmentModule(Fragment fragment) {
        this.fragment = fragment;
    }

    @Provides
    public Context provideContext() {
        return fragment.getContext();
    }

    @Provides
    public Fragment provideFragment() {
        return fragment;
    }

    @Provides
    public FragmentManager provideFragmentManager() {
        return fragment.getFragmentManager();
    }
}
