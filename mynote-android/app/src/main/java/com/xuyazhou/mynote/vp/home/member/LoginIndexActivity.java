package com.xuyazhou.mynote.vp.home.member;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.widget.TextView;

import com.xuyazhou.mynote.R;
import com.xuyazhou.mynote.common.widget.IconTextView;
import com.xuyazhou.mynote.vp.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginIndexActivity extends BaseActivity<LoginIndexPresenter> implements LoginIndexContract.View {

    @BindView(R.id.logo)
    IconTextView logo;
    @BindView(R.id.domain_text)
    TextView domainText;
    @BindView(R.id.btn_sign_up)
    AppCompatButton btnSignUp;
    @BindView(R.id.btn_sign_in)
    AppCompatButton btnSignIn;
    @BindView(R.id.btn_sign_in_google)
    AppCompatButton btnSignInGoogle;

    @Override
    protected void setupActivityComponent() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_login_index_layout;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.addSub();
        initUI();

    }

    @Override
    public void initUI() {
    }


    @OnClick(R.id.btn_sign_up)
    public void onBtnSignUpClicked() {
        startActivity(new Intent(this, RegistAcivity.class));
    }

    @OnClick(R.id.btn_sign_in)
    public void onBtnSignInClicked() {
        startActivity(new Intent(this, LoginActivity.class));
    }

    @OnClick(R.id.btn_sign_in_google)
    public void onBtnSignInGoogleClicked() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.Unsubscribe();
    }
}