package com.xuyazhou.mynote.handler;

import com.xuyazhou.mynote.exception.MemberException;
import com.xuyazhou.mynote.model.User;
import com.xuyazhou.mynote.model.bean.MemberTokenBean;
import com.xuyazhou.mynote.model.bean.MemberTokenType;

/**
 * Author: lampard_xu(xuyazhou18@gmail.com)
 * *
 * Date: 2017-04-06
 */
public interface IMemberHandler {

    /**
     * 生成ID
     */
    Integer getseq(String tableName);

    boolean existEmail(String mobilePhone);

    /**
     * 添加用户
     */
    int saveMember(User usrBean, String password);

    /**
     * 根据手机号获取密码
     */
    String getPassWordByemail(String email) throws MemberException;

    /**
     * 根据邮箱获取会员对象
     */
    User getUersByemail(String email);

    User getUersByuserId(String token);

    /**
     * 保存或者更新MemberToken
     */
    void saveOrUpdateMemberToken(String memberId, String token, MemberTokenType type);


    /**
     * 根据会员id获取UserToken信息
     *
     * @return UserTokenBean
     */
    MemberTokenBean getMemberToken(String memberId);

    void updateCheckPoint(User user);

}
