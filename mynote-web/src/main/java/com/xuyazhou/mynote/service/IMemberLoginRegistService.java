package com.xuyazhou.mynote.service;

import com.xuyazhou.mynote.exception.MemberException;
import com.xuyazhou.mynote.model.bean.UserbeanBack;

import java.util.Map;

/**
 * Author: lampard_xu(xuyazhou18@gmail.com)
 * *
 * Date: 2017-04-06
 */
public interface IMemberLoginRegistService {


    UserbeanBack regist(String email, String password) throws MemberException;

    UserbeanBack login(String randomToken, String loginName, String password) throws MemberException;

    String registerlogin(String id) throws MemberException;

    String getRandomToken(String Sid);

    String getuserIdbyaccesstoken(String accessToken) throws MemberException;

    Map<String, String> getNonce();

    void logout(String token);

    String checkLogin(String p_accessToken);
}
