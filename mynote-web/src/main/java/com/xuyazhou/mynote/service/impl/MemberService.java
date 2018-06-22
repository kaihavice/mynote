package com.xuyazhou.mynote.service.impl;

import com.xuyazhou.mynote.handler.IMemberHandler;
import com.xuyazhou.mynote.model.bean.MemberTokenType;
import com.xuyazhou.mynote.service.IMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Author: lampard_xu(xuyazhou18@gmail.com)
 * *
 * Date: 2017-04-06
 */
@Service
public class MemberService implements IMemberService {

    @Autowired
    private IMemberHandler memberHandler;

    @Override
    public boolean existemail(String mail) {
        return memberHandler.existEmail(mail);
    }

    @Override
    public void saveOrUpdateMemberToken(String memberId, String token, MemberTokenType type) {
        memberHandler.saveOrUpdateMemberToken(memberId, token, type);
    }
}
