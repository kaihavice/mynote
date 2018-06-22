package com.xuyazhou.mynote.common.inject.models;

import android.content.Context;
import android.content.res.Resources;

import com.xuyazhou.mynote.MyApplication;
import com.xuyazhou.mynote.model.data.DataManager;
import com.xuyazhou.mynote.model.event.RxBus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

/**
 * 全局声明的一些依赖变量声明
 * Author: lampard_xu(xuyazhou18@gmail.com)
 * <p>
 * Date: 2016-01-28
 */
@Module
public class AppModule {

    private MyApplication myApplication;

    public AppModule(MyApplication myApplication) {
        this.myApplication = myApplication;
    }

    @Provides
    @Singleton
    MyApplication providesApplication() {
        return myApplication;
    }


    @Provides
    @Singleton
    Context providesContext() {
        return myApplication;
    }

    @Provides
    @Singleton
    protected Resources provideResources() {
        return myApplication.getResources();
    }

    @Provides
    @Singleton
    protected DataManager provideDataManager() {
        return new DataManager();
    }


    @Provides
    @Singleton
    protected RxBus provideRxbus() {//事件总线
        return new RxBus();
    }

    @Provides
    protected CompositeDisposable provideSubsccribe() {
        return new CompositeDisposable();
    }
}


