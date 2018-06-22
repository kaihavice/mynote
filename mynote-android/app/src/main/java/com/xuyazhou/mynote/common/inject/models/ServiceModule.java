package com.xuyazhou.mynote.common.inject.models;

import android.app.Service;

import dagger.Module;
import dagger.Provides;

/**
 * Author: lampard_xu(xuyazhou18@gmail.com)
 * <p/>
 * Date: 2016-03-10
 */
@Module
public class ServiceModule {
    private Service service;

    public ServiceModule(Service service) {
        this.service = service;
    }

    @Provides
    public Service provideService() {
        return service;
    }
}
