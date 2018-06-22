package com.xuyazhou.mynote.common.inject.components;


import com.xuyazhou.mynote.MyApplication;
import com.xuyazhou.mynote.common.inject.models.ApiServiceModule;
import com.xuyazhou.mynote.common.inject.models.AppModule;
import com.xuyazhou.mynote.model.api.ApiService;
import com.xuyazhou.mynote.model.api.ApiWrapper;
import com.xuyazhou.mynote.model.data.DataManager;
import com.xuyazhou.mynote.model.event.RxBus;

import javax.inject.Singleton;

import dagger.Component;
import io.reactivex.disposables.CompositeDisposable;

/**
 * app全局的一些注入
 * *
 * Author: lampard_xu(xuyazhou18@gmail.com)
 * *
 * Date: 2016-04-26
 */
@Component(
        modules = {
                //依赖的一些模块
                AppModule.class,
                ApiServiceModule.class
        }
)

@Singleton
public interface AppComponent {

    MyApplication inject(MyApplication app);

    ApiService apiService();

    RxBus getRxbus();

    MyApplication getApplication();

    CompositeDisposable getSubscription();

    ApiWrapper getApiWrapper();

    DataManager getDataManger();

}
