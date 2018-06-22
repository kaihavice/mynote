package com.xuyazhou.mynote.common.widget;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.xuyazhou.mynote.R;
import com.xuyazhou.mynote.vp.adapter.NoteDeatilsListAdapter;

/**
 * Author: lampard_xu(xuyazhou18@gmail.com)
 * *
 * Date: 2017/2/9
 */
public class MyItemTouchHelperCallback extends ItemTouchHelper.Callback {


    private NoteDeatilsListAdapter adapter;

    public MyItemTouchHelperCallback(NoteDeatilsListAdapter adapter) {
        this.adapter = adapter;
    }


    /**
     * 设置Drag/Swipe的Flag
     * 这里我们把滑动(Drag)的四个方向全都设置上了,说明Item可以随意移动
     * 然后把删除(暂且叫删除/swipe)的方向设置为Start和End,说明可以水平拖动删除
     */
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
            final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            final int swipeFlags = 0;
            return makeMovementFlags(dragFlags, swipeFlags);
        } else {
            final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            final int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
            return makeMovementFlags(dragFlags, swipeFlags);
        }
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        if (viewHolder.getAdapterPosition() == 0 && target.getAdapterPosition() == 0) {

            return false;
        }

        if (viewHolder.getItemViewType() != target.getItemViewType()) {
            return false;
        }

        adapter.onMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;


    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        adapter.onSwipe(viewHolder.getAdapterPosition());
    }

    /**
     * Item是否能被Swipe到dismiss
     * 也就是删除这条数据
     */
    @Override
    public boolean isItemViewSwipeEnabled() {
        return false;
    }

    /**
     * Item长按是否可以拖拽
     */
    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }


    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        super.onSelectedChanged(viewHolder, actionState);
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE && viewHolder instanceof NoteDeatilsListAdapter.ItemCheckListViewHolder) {
            NoteDeatilsListAdapter.ItemCheckListViewHolder holder = (NoteDeatilsListAdapter.ItemCheckListViewHolder) viewHolder;
            holder.itemView.setBackground(holder.itemView.getResources().getDrawable(R.drawable.bg_detail_attach));
        }
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);

        if (viewHolder instanceof NoteDeatilsListAdapter.ItemCheckListViewHolder) {

        } else {
            NoteDeatilsListAdapter.ItemCheckListViewHolder holder = (NoteDeatilsListAdapter.ItemCheckListViewHolder) viewHolder;
            holder.itemView.setBackgroundColor(0xffffff);
        }
    }
}
