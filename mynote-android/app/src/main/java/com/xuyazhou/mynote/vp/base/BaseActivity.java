package com.xuyazhou.mynote.vp.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.umeng.analytics.MobclickAgent;
import com.xuyazhou.mynote.MyApplication;
import com.xuyazhou.mynote.R;
import com.xuyazhou.mynote.common.inject.components.ActivityComponent;
import com.xuyazhou.mynote.common.inject.components.AppComponent;
import com.xuyazhou.mynote.common.inject.components.DaggerActivityComponent;
import com.xuyazhou.mynote.common.inject.models.ActivityModule;
import com.xuyazhou.mynote.vp.home.HomeActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * activity 的基类
 * Author: lampard_xu(xuyazhou18@gmail.com)
 * *
 * Date: 2016-03-09
 */
public abstract class BaseActivity<P extends BasePresenter> extends RxAppCompatActivity implements IBaseView {

    @Inject
    protected P presenter;//控制器
    @Inject
    protected
    MyApplication app;//全局的appLication
    @Nullable
    @BindView(R.id.toolbar)
    protected
    Toolbar toolbar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupActivityComponent();
        setContentView(getLayoutResourceId());
        ButterKnife.bind(this);
        presenter.setView(this);

        configureToolbar();
        app.addActivity(this);//记录每一个创建的activity


    }


    @Override
    protected void onDestroy() {

        if (presenter != null) {
            presenter.onDestroy();
        }
        super.onDestroy();

    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);//友盟的后台统计

    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);//友盟的后台统计
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    ActivityComponent activityComponent;

    //得到注入的activityComponent
    protected ActivityComponent getActivityComponent() {
        if (activityComponent == null) {
            activityComponent = DaggerActivityComponent.builder()
                    .appComponent(getAppComponent())
                    .activityModule(getActivityModule())
                    .build();
        }
        return activityComponent;
    }

    protected ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }

    protected AppComponent getAppComponent() {
        return ((MyApplication) getApplication()).getAppComponent();
    }


    //  配置头布局
    private void configureToolbar() {
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);

            if (this instanceof HomeActivity) {


            } else {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//                if (this instanceof NoteDetailActivity) {
//
//                } else {
//                    getSupportActionBar().setHomeAsUpIndicator(R.mipmap.abc_ic_ab_back_mtrl_am_alpha);
//                }

                toolbar.setNavigationOnClickListener(v -> finish());
            }


        }
    }

    protected abstract void setupActivityComponent();

    protected abstract int getLayoutResourceId();


}
