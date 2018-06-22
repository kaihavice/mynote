package com.xuyazhou.mynote.common.utils;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Author: lampard_xu(xuyazhou18@gmail.com)
 * <p>
 * Date: 2016-11-08
 */

public class DataUtils {

    private ArrayList<Object> showList;

    @Inject
    public DataUtils() {
        showList = new ArrayList<>();
    }

    public <T> ArrayList<T> SetListData(Context context, boolean isloadMore, List<T> datalist) {


        if (isloadMore) {
            if (datalist.size() > 0) {
                showList.addAll(datalist);
            } else {
                ShowToast.Short(context, "数据已经全部加载完毕");
            }
        } else {

            showList.clear();

            if (datalist != null) {

                showList.addAll(datalist);
            } else {

                showList.addAll(new ArrayList<>());
            }

        }


        return (ArrayList<T>) showList;
    }


}
