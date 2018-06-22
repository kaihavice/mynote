package com.xuyazhou.mynote.vp.home.member;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.xuyazhou.mynote.R;
import com.xuyazhou.mynote.common.utils.TimeUtils;
import com.xuyazhou.mynote.vp.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class UserinfoActvity extends BaseActivity<UserinfoPresenter> implements UserinfoContract.View {

    @BindView(R.id.avatar)
    ImageView avatar;
    @BindView(R.id.layout_avatar)
    RelativeLayout layoutAvatar;
    @BindView(R.id.email_title)
    TextView emailTitle;
    @BindView(R.id.email_summary)
    TextView emailSummary;
    @BindView(R.id.layout_email)
    RelativeLayout layoutEmail;
    @BindView(R.id.title_sync_time)
    TextView titleSyncTime;
    @BindView(R.id.summary_sync_time)
    TextView summarySyncTime;
    @BindView(R.id.layout_sync_time)
    RelativeLayout layoutSyncTime;

    @BindView(R.id.sign_out_btn)
    AppCompatButton signOutBtn;

    @Override
    protected void setupActivityComponent() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_account_info;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initUI();

    }

    @Override
    public void initUI() {
        toolbar.setTitle("个人中心");
        emailSummary.setText(presenter.getUser().getEmail());
        if (!presenter.getUser().getAvatar().equals("")) {
            Glide.with(this).load(presenter.getUser().getAvatar())
                    .bitmapTransform(new CropCircleTransformation(
                            this))
                    .error(R.mipmap.default_photo_light)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(avatar);
        }

        summarySyncTime.setText(TimeUtils.getFormatTime(presenter.getUser().getLastSyncNoteTime(), "yyyy/MM/dd HH:mm"));


    }

    @OnClick({R.id.layout_avatar, R.id.sign_out_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_avatar:
                break;
            case R.id.sign_out_btn:

                presenter.logout();
                this.finish();
                break;
        }
    }


}