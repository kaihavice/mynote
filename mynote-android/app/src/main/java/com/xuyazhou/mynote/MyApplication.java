package com.xuyazhou.mynote;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;
import com.miguelbcr.ui.rx_paparazzo2.RxPaparazzo;
import com.raizlabs.android.dbflow.config.DatabaseConfig;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.xuyazhou.mynote.common.config.Constant;
import com.xuyazhou.mynote.common.inject.components.AppComponent;
import com.xuyazhou.mynote.common.inject.components.DaggerAppComponent;
import com.xuyazhou.mynote.common.inject.models.ApiServiceModule;
import com.xuyazhou.mynote.common.inject.models.AppModule;
import com.xuyazhou.mynote.model.db.AppDatabase;
import com.xuyazhou.mynote.model.db.User;
import com.xuyazhou.mynote.model.db.UserSetting;

import java.util.LinkedList;
import java.util.List;

/**
 * app的全局配置
 * Author: lampard_xu(xuyazhou18@gmail.com)
 * <p>
 * Date: 2016-04-26
 */
public class MyApplication extends Application {
    AppComponent appComponent = null;

    public void setAppComponent(AppComponent appComponent) {
        this.appComponent = appComponent;
    }

    private List<Activity> activityList = new LinkedList<>();

    @Override
    public void onCreate() {
        super.onCreate();

        Stetho.initializeWithDefaults(this);
        RxPaparazzo.register(this);

        initDB();

        initInject();


    }

    private void initDB() {


        FlowManager.init(FlowConfig.builder(this)
                .addDatabaseConfig(DatabaseConfig.builder(AppDatabase.class)
                        .databaseName("mynote")
                        .build())
                .build());




        if (SQLite.select().from(UserSetting.class).querySingle() == null) {
            UserSetting userSetting = new UserSetting();
            userSetting.setCratedTime(System.currentTimeMillis() / 1000);
            userSetting.setModifiedTime(System.currentTimeMillis() / 1000);
            userSetting.setListType(Constant.GRID);
            userSetting.save();
        }

//        if (SQLite.select().from(UserSetting.class).querySingle() == null) {
//            UserSetting config = new UserSetting();
//            config.setCratedTime(System.currentTimeMillis());
//            config.save();
//        }


        if (SQLite.select().from(User.class).querySingle() == null) {
            User userinfo = new User();
            userinfo.save();
        }

    }


    private void initInject() {
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .apiServiceModule(new ApiServiceModule())
                .build();


    }


    public static MyApplication get(Context context) {
        return (MyApplication) context.getApplicationContext();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    // 添加Activity到容器中
    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    // 遍历所有Activity并finish
    public void exit() {

        for (Activity activity : activityList) {
            activity.finish();
        }
        System.exit(0);
    }
}
