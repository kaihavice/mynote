package com.xuyazhou.mynote.common.inject.models;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;

import dagger.Module;
import dagger.Provides;

/**
 * activity需要方便注入的一些变量的声明
 *
 * Author: lampard_xu(xuyazhou18@gmail.com)
 * <p/>
 * Date: 2016-03-03
 */
@Module
public class ActivityModule {

    final AppCompatActivity activity;

    public ActivityModule(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Provides
    public Activity provideActivity() {
        return activity;
    }

    @Provides
    public Context provideContext() {
        return activity;
    }

    @Provides
    LayoutInflater provideLayoutInflater() {
        return activity.getLayoutInflater();
    }
}
