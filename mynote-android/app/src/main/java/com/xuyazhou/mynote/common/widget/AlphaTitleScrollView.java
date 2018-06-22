package com.xuyazhou.mynote.common.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;

/**
 * Author: lampard_xu(xuyazhou18@gmail.com)
 * *
 * Date: 2017/3/9
 *  android:fillViewport="true"
 */
public class AlphaTitleScrollView extends ScrollView {

    private View mReferenceView;
    private View mTitleView;
    private int backgroundColor;
    private int[] backgroundColorRGB;
    private boolean isSlowlyChange = true;

    public AlphaTitleScrollView(Context context) {
        super(context);
    }

    public AlphaTitleScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AlphaTitleScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AlphaTitleScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @Override
    public void scrollTo(int x, int y) {
        if (x == 0 && y == 0 || y <= 0) {
            super.scrollTo(x, y);
        }
    }

    public void setSlowlyChange(boolean slowlyChange) {
        this.isSlowlyChange = slowlyChange;
    }

    /**
     * 初始化设置参数
     *
     * @param titleView
     * @param referenceView
     * @param backgroundColor
     * @param backgroundColorRGB
     */
    public void initAlphaTitle(View titleView, View referenceView, int backgroundColor, int[] backgroundColorRGB) {
        this.mTitleView = titleView;
        this.mReferenceView = referenceView;
        this.backgroundColor = backgroundColor;
        this.backgroundColorRGB = backgroundColorRGB;
    }

    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);

        if (scrollY >= mReferenceView.getTop() + mReferenceView.getMeasuredHeight()) {
            mTitleView.setBackgroundColor(backgroundColor);
        } else if (scrollY >= 0) {
            if (!isSlowlyChange) {
                mTitleView.setBackgroundColor(Color.TRANSPARENT);
            } else {
                float persent = scrollY * 1f / (mReferenceView.getTop() + mReferenceView.getMeasuredHeight());
                int alpha = (int) (255 * persent);
                int color = Color.argb(alpha, backgroundColorRGB[0], backgroundColorRGB[1], backgroundColorRGB[2]);
                mTitleView.setBackgroundColor(color);
            }
        }

    }
}
