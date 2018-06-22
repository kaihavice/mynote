package com.xuyazhou.mynote.common.utils;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.xuyazhou.mynote.common.widget.DividerGridItemDecoration;
import com.xuyazhou.mynote.common.widget.EndlessRecyclerOnScrollListener;
import com.xuyazhou.mynote.common.widget.SpaceItemDecorationFirstSpace;


/**
 * Author: lampard_xu(xuyazhou18@gmail.com)
 * <p>
 * Date: 2016-11-08
 */

public class RecyclerViewUtils {


    public static void setRecyclerView(Context context, RecyclerView recyclerView,
                                       RecyclerView.Adapter adapter, int space, loadMoreListener listener) {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(new SpaceItemDecorationFirstSpace(DensityUtil.dp2px(context, space)));
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadNextPage(View view) {
                super.onLoadNextPage(view);

                listener.loadMore();
            }
        });
    }

    public static void setGridRecyclerView(Context context, RecyclerView recyclerView,
                                           RecyclerView.Adapter adapter, int space, loadMoreListener listener) {

        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 2);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(new DividerGridItemDecoration(DensityUtil.dp2px(context, space),2));
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadNextPage(View view) {
                super.onLoadNextPage(view);

                listener.loadMore();
            }
        });
    }

    public interface loadMoreListener {
        void loadMore();
    }
}
