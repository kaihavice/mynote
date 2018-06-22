package com.xuyazhou.mynote.handler;

import com.xuyazhou.mynote.model.CheckListItem;

/**
 * Author: lampard_xu(xuyazhou18@gmail.com)
 * *
 * Date: 2017-05-15
 */
public interface ICheckListHandler {

    CheckListItem getCheckListById(String id);

    int UpdateCheckList(CheckListItem checkListItem);

    int SaveCheckList(CheckListItem checkListItem);
}
