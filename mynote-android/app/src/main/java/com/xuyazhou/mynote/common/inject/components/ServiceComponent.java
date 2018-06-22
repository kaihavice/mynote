package com.xuyazhou.mynote.common.inject.components;


import com.xuyazhou.mynote.common.inject.models.ServiceModule;
import com.xuyazhou.mynote.common.inject.scope.ServiceScope;

import dagger.Component;


/**
 * service的注入声明
 * <p>
 * Author: lampard_xu(xuyazhou18@gmail.com)
 * <p>
 * Date: 2016-03-10
 */
@Component(
        dependencies = AppComponent.class,
        modules = ServiceModule.class
)
@ServiceScope
public interface ServiceComponent {
}
