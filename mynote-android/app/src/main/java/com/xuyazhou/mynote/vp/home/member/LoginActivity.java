package com.xuyazhou.mynote.vp.home.member;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.xuyazhou.mynote.R;
import com.xuyazhou.mynote.common.utils.StringUtil;
import com.xuyazhou.mynote.vp.base.BaseActivity;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View {

    @BindView(R.id.username)
    AppCompatEditText username;
    @BindView(R.id.password)
    AppCompatEditText password;
    @BindView(R.id.btn_sign_in)
    AppCompatButton btnSignIn;
    @BindView(R.id.forget_password)
    TextView forgetPassword;

    @Override
    protected void setupActivityComponent() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_login_layout;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initUI();
        loginClick();

    }

    @Override
    public void initUI() {

        toolbar.setTitle("登陆");
    }

    @Override
    public void forgetPasswordClick() {

    }

    @Override
    public void loginClick() {
        RxView.clicks(btnSignIn)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .filter(aVoid -> StringUtil.checkIsNotEmpty(username, "邮箱"))
                .filter(aVoid -> StringUtil.checkIsNotEmpty(password, "密码"))
                .filter(aVoid -> StringUtil.ForamtError(username, "邮箱格式不对"))
                .filter(aVoid -> StringUtil.checkBigLenght(password, 6))
                .subscribe(aVoid ->
                        presenter.LoginDo(username.getText().toString().trim(), password.getText().toString().trim()));
    }

    @Override
    public void loginSuccuess() {
        this.finish();
    }

    @OnClick(R.id.forget_password)
    public void forgetPwClick() {

    }
}