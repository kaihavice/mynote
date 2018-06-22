package com.xuyazhou.mynote.vp.home.member;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;

import com.jakewharton.rxbinding2.view.RxView;
import com.xuyazhou.mynote.R;
import com.xuyazhou.mynote.common.utils.StringUtil;
import com.xuyazhou.mynote.vp.base.BaseActivity;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;

public class RegistAcivity extends BaseActivity<RegistPresenter> implements RegistContract.View {


    @BindView(R.id.btn_sign_up)
    AppCompatButton btnSignUp;
    @BindView(R.id.username)
    AppCompatEditText username;
    @BindView(R.id.password)
    AppCompatEditText password;

    @Override
    protected void setupActivityComponent() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_regist_acivity;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initUI();
        registerClick();
    }

    @Override
    public void initUI() {
        toolbar.setTitle("注册");
    }

    @Override
    public void registerClick() {
        RxView.clicks(btnSignUp)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .filter(aVoid -> StringUtil.checkIsNotEmpty(username, "邮箱"))
                .filter(aVoid -> StringUtil.checkIsNotEmpty(password, "密码"))
                .filter(aVoid -> StringUtil.ForamtError(username, "邮箱格式不对"))
                .filter(aVoid -> StringUtil.checkBigLenght(password, 6))
                .subscribe(aVoid ->
                        presenter.regist(username.getText().toString().trim(), password.getText().toString().trim()));
    }

    @Override
    public void registerSuccuess() {
        finish();
    }


}