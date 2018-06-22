package com.xuyazhou.mynote.common.widget;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 *
 * 列表间隙
 *
 * Author: lampard_xu(xuyazhou18@gmail.com)
 * <p>
 * Date: 2016-02-17
 */
public class SpaceItemDecorationFirstSpace extends RecyclerView.ItemDecoration {

    private int space;

    public SpaceItemDecorationFirstSpace(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {


            outRect.top = space;
    }
}
