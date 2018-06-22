package com.xuyazhou.mynote.common.inject.scope;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * 声明activity的作用域和生命周期
 *
 * Author: lampard_xu(xuyazhou18@gmail.com)
 * <p/>
 * Date: 2016-01-28
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface ActivityScope {
}
