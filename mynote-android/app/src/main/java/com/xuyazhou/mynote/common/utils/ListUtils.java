package com.xuyazhou.mynote.common.utils;

import com.xuyazhou.mynote.model.db.CheckListItem;

import java.util.ArrayList;

/**
 * Author: lampard_xu(xuyazhou18@gmail.com)
 * *
 * Date: 2017/2/8
 */
public class ListUtils {

    public static ArrayList<CheckListItem> getCheckList(ArrayList<CheckListItem> checkList, ArrayList<CheckListItem> checkListDone) {


        checkList.addAll(checkListDone);

        return checkList;
    }

}
