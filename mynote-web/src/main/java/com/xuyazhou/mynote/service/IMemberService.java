package com.xuyazhou.mynote.service;

import com.xuyazhou.mynote.model.bean.MemberTokenType;

/**
 * Author: lampard_xu(xuyazhou18@gmail.com)
 * *
 * Date: 2017-04-06
 */

public interface IMemberService {

    boolean existemail(String phone);

    void saveOrUpdateMemberToken(String id, String token, MemberTokenType type);

}
