package com.xuyazhou.mynote.common.widget;

/**
 * Author: lampard_xu(xuyazhou18@gmail.com)
 * *
 * Date: 2017/2/9
 */
public interface OnItemDragbackListener {
    void onMove(int fromPosition, int toPosition);

    void onSwipe(int position);
}
