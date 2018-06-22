package com.xuyazhou.mynote.vp.home.detail.image;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;

import com.xuyazhou.mynote.R;
import com.xuyazhou.mynote.common.utils.DialogUtil;
import com.xuyazhou.mynote.common.widget.IconTextView;
import com.xuyazhou.mynote.model.db.AttachMent;
import com.xuyazhou.mynote.vp.adapter.ImagePagerAdapter;
import com.xuyazhou.mynote.vp.base.BaseActivity;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class ImageExpandActivity extends BaseActivity<ImageExpandPresenter> implements ImageExpandContract.View, ViewPager.OnPageChangeListener {

    @BindView(R.id.delete_btn)
    IconTextView deleteBtn;
    @BindView(R.id.rotate_btn)
    IconTextView rotateBtn;
    @BindView(R.id.imageViewPager)
    ViewPager imageViewPager;

    @Inject
    ImagePagerAdapter adapter;

    private int currentPosition;


    @Override
    protected void setupActivityComponent() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_image_expand;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initUI();

        presenter.getImagList();

    }

    @Override
    public void initUI() {
        currentPosition = getIntent().getIntExtra("position", 0);

    }

    @Override
    public void showImage(ArrayList<AttachMent> imagelist) {
        adapter.setData(imagelist);
        imageViewPager.setAdapter(adapter);

        if (currentPosition >= 0) {
            imageViewPager.setCurrentItem(currentPosition);
        }

    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        currentPosition = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {


    }


    @OnClick(R.id.delete_btn)
    public void deleteClick() {
        DialogUtil.showConfirmDialog(this, "删除图片", "确定要删除吗？", "删除",
                (dialog, which) -> {
                    presenter.deleteAttach(currentPosition);

                    currentPosition = currentPosition - 1;
                    ImageExpandActivity.this.finish();

                });
    }
}