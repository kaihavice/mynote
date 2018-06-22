package com.xuyazhou.mynote.common.inject.scope;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Author: lampard_xu(xuyazhou18@gmail.com)
 * <p>
 * Date: 2016-03-10
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface ServiceScope {
}
