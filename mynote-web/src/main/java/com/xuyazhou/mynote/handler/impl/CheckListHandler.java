package com.xuyazhou.mynote.handler.impl;

import com.xuyazhou.mynote.handler.ICheckListHandler;
import com.xuyazhou.mynote.mapper.CheckListItemMapper;
import com.xuyazhou.mynote.model.CheckListItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Author: lampard_xu(xuyazhou18@gmail.com)
 * *
 * Date: 2017-05-15
 */
@Service
public class CheckListHandler implements ICheckListHandler {

    @Autowired
    private CheckListItemMapper checkListItemMapper;

    @Override
    public CheckListItem getCheckListById(String id) {
        return null;
    }

    @Override
    public int UpdateCheckList(CheckListItem checkListItem) {
        return 0;
    }

    @Override
    public int SaveCheckList(CheckListItem checkListItem) {
        return 0;
    }
}
