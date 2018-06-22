package com.xuyazhou.mynote.vp.base;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trello.rxlifecycle2.components.support.RxFragment;
import com.xuyazhou.mynote.MyApplication;
import com.xuyazhou.mynote.common.inject.components.AppComponent;
import com.xuyazhou.mynote.common.inject.components.DaggerFragmentComponent;
import com.xuyazhou.mynote.common.inject.components.FragmentComponent;
import com.xuyazhou.mynote.common.inject.models.FragmentModule;

import javax.inject.Inject;

import butterknife.ButterKnife;

/**
 * fragment的基类
 * <p>
 * Author: lampard_xu(xuyazhou18@gmail.com)
 * <p>
 * Date: 2016-03-03
 */
public abstract class BaseFragment<P extends BasePresenter> extends RxFragment implements IBaseView {

    @Inject
    protected P presenter;//控制器
    protected boolean isVisible;//当前fragment是否已经显示
    protected boolean isPrepared;//是否准备加载数据


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        setupFragmentComponent();//创建每个fragment的dagger component
        presenter.setView(this);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflateView(inflater, container);//布局的填充
        ButterKnife.bind(this, view);//view的注入绑定
        return view;
    }

    @Override
    public void onDestroyView() {

        super.onDestroyView();
        if (presenter != null) {
            presenter.onDestroy();
        }

    }

    protected FragmentComponent getFragmentComponent() {
        return DaggerFragmentComponent.builder()
                .appComponent(getAppComponent())
                .fragmentModule(getFragmentModule())
                .build();
    }

    protected AppComponent getAppComponent() {
        return ((MyApplication) getActivity().getApplication()).getAppComponent();
    }

    protected FragmentModule getFragmentModule() {
        return new FragmentModule(this);
    }

    /**
     * 在这里实现Fragment数据的缓加载.
     *
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

//    //  根据当前界面数据加载状态，判定当前是否还能加载其他界面数据
//    protected boolean canLoadData(@NonNull MultiStateView multiStateView, @NonNull RecyclerView.Adapter adapter) {
//        @ViewState int viewState = multiStateView.getViewState();
//        return !(viewState == MultiStateView.VIEW_STATE_LOADING ||
//                (viewState == MultiStateView.VIEW_STATE_CONTENT && adapter.getItemCount() > 0) ||
//                viewState == MultiStateView.VIEW_STATE_EMPTY ||
//                viewState == MultiStateView.VIEW_STATE_ERROR);
//    }


    //  当前用户视图显示
    protected void onVisible() {
        lazyLoad();
    }

    //  视图数据延迟加载
    protected abstract void lazyLoad();

    //  当前用户视图不显示
    protected void onInvisible() {
    }

    //  设置容器依赖注入注册
    protected abstract void setupFragmentComponent();

    //  填充布局
    protected abstract View inflateView(LayoutInflater inflater, ViewGroup container);
}
