package com.xuyazhou.mynote.common.widget;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Author: lampard_xu(xuyazhou18@gmail.com)
 * *
 * Date: 2016/12/20
 */
public class MyNoteSwipeRefreshLayout extends SwipeRefreshLayout {

    private RecyclerView mRecyclerView;



    private OnSwipeDisableListener onSwipeDisableListener;

    public MyNoteSwipeRefreshLayout(Context paramContext) {
        super(paramContext);
    }

    public MyNoteSwipeRefreshLayout(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
    }

    @Override
    public boolean canChildScrollUp() {
        if ((this.onSwipeDisableListener != null) && (!this.onSwipeDisableListener.allowed())) {
            return true;
        }
        if (this.mRecyclerView != null) {
            return ViewCompat.canScrollVertically(this.mRecyclerView, -1);
        }
        return super.canChildScrollUp();
    }

    @Override
    public boolean onTouchEvent(MotionEvent paramMotionEvent) {
        try {
            boolean bool = super.onTouchEvent(paramMotionEvent);
            return bool;
        } catch (Exception paramMotion) {
        }
        return true;
    }



    public void setRecyclerView(RecyclerView paramRecyclerView) {
        this.mRecyclerView = paramRecyclerView;
    }

    public void setOnSwipeDisableListener(OnSwipeDisableListener onSwipeDisableListener) {
        this.onSwipeDisableListener = onSwipeDisableListener;
    }

    public   interface OnSwipeDisableListener {
         boolean allowed();
    }
}


