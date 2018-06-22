package com.xuyazhou.mynote.common.inject.components;


import com.xuyazhou.mynote.common.inject.models.FragmentModule;
import com.xuyazhou.mynote.common.inject.scope.FragmentScope;

import dagger.Component;


/**
 *
 * 全部的 fragment 注入声明
 *
 * Author: lampard_xu(xuyazhou18@gmail.com)
 * <p>
 * Date: 2016-03-03
 */
@Component(
        dependencies = AppComponent.class,
        modules = FragmentModule.class
)

@FragmentScope
public interface FragmentComponent {



}
