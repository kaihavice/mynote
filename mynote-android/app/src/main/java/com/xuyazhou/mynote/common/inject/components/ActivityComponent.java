package com.xuyazhou.mynote.common.inject.components;


import com.xuyazhou.mynote.common.inject.models.ActivityModule;
import com.xuyazhou.mynote.common.inject.scope.ActivityScope;
import com.xuyazhou.mynote.vp.home.detail.NoteDetailActivity;
import com.xuyazhou.mynote.vp.home.detail.file.FileBrowserActivity;
import com.xuyazhou.mynote.vp.home.detail.image.ImageExpandActivity;
import com.xuyazhou.mynote.vp.home.HomeActivity;
import com.xuyazhou.mynote.vp.home.member.LoginActivity;
import com.xuyazhou.mynote.vp.home.member.LoginIndexActivity;
import com.xuyazhou.mynote.vp.home.member.RegistAcivity;
import com.xuyazhou.mynote.vp.home.member.UserinfoActvity;

import dagger.Component;


/**
 * 全部的activity 注入声明
 * Author: lampard_xu(xuyazhou18@gmail.com)
 * <p>
 * Date: 2016-04-26
 */
@Component(
        //所依赖的一些模块
        dependencies = AppComponent.class,
        modules = ActivityModule.class
)

@ActivityScope
public interface ActivityComponent {




    void inject(HomeActivity homeActivity);

    void inject(NoteDetailActivity noteDetailActivity);

    void inject(ImageExpandActivity imageExpandActivity);

    void inject(FileBrowserActivity fileBrowserActivity);

    void inject(LoginIndexActivity loginIndexActivity);

    void inject(LoginActivity loginActivity);

    void inject(RegistAcivity registAcivity);

    void inject(UserinfoActvity userinfoActvity);
}
