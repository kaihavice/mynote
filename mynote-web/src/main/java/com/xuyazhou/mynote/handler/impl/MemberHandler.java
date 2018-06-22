package com.xuyazhou.mynote.handler.impl;

import com.xuyazhou.mynote.Utils.BeanUtil;
import com.xuyazhou.mynote.exception.MemberException;
import com.xuyazhou.mynote.handler.IMemberHandler;
import com.xuyazhou.mynote.mapper.MemberTokenMapper;
import com.xuyazhou.mynote.mapper.TableSeqMapper;
import com.xuyazhou.mynote.mapper.UserMapper;
import com.xuyazhou.mynote.model.MemberToken;
import com.xuyazhou.mynote.model.MemberTokenExample;
import com.xuyazhou.mynote.model.User;
import com.xuyazhou.mynote.model.UserExample;
import com.xuyazhou.mynote.model.bean.MemberTokenBean;
import com.xuyazhou.mynote.model.bean.MemberTokenType;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Author: lampard_xu(xuyazhou18@gmail.com)
 * *
 * Date: 2017-04-06
 */
@Service
public class MemberHandler implements IMemberHandler {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private TableSeqMapper tableSeqMapper;

    @Autowired
    private MemberTokenMapper memberTokenMapper;

    @Override
    public Integer getseq(String tableName) {
        return tableSeqMapper.getSeq(tableName);
    }

    @Override
    public boolean existEmail(String mail) {

        if (StringUtils.isNotEmpty(mail)) {
            UserExample userExample = new UserExample();
            UserExample.Criteria criteria = userExample.createCriteria();
            criteria.andEmailEqualTo(mail);
            return userMapper.countByExample(userExample) > 0;
        }
        return false;
    }

    @Override
    public int saveMember(User usrBean, String password) {
        User user = new User();
        BeanUtils.copyProperties(usrBean, user);
        user.setPassword(password);
        return userMapper.insertSelective(user);
    }

    @Override
    public String getPassWordByemail(String email) throws MemberException {
        if (StringUtils.isNotEmpty(email)) {
            UserExample userExample = new UserExample();
            UserExample.Criteria criteria = userExample.createCriteria();
            criteria.andEmailEqualTo(email);
            List<User> list = userMapper.selectByExample(userExample);
            if (CollectionUtils.isNotEmpty(list)) {
                if (1 == list.get(0).getStatus()) {
                    throw new MemberException(MemberException.ACCOUNT_IS_DISABLED);
                }
                return list.get(0).getPassword();
            }
        }
        throw new MemberException(MemberException.LOGIN_NAME_OR_PWD_ERROR);
    }

    @Override
    public User getUersByemail(String email) {
        if (StringUtils.isNotEmpty(email)) {
            UserExample userExample = new UserExample();
            UserExample.Criteria criteria = userExample.createCriteria();
            criteria.andEmailEqualTo(email);
            List<User> list = userMapper.selectByExample(userExample);
            if (CollectionUtils.isNotEmpty(list)) {
                return BeanUtil.getBean(list.get(0), User.class);
            }
        }
        return null;
    }

    @Override
    public User getUersByuserId(String userId) {


        if (StringUtils.isNotEmpty(userId)) {
            UserExample userExample = new UserExample();
            UserExample.Criteria criteria = userExample.createCriteria();
            criteria.andSidEqualTo(userId);
            List<User> list = userMapper.selectByExample(userExample);
            if (CollectionUtils.isNotEmpty(list)) {
                return BeanUtil.getBean(list.get(0), User.class);

            }
        }
        return null;
    }


    @Override
    public void saveOrUpdateMemberToken(String memberId, String token, MemberTokenType type) {
        if (StringUtils.isNotEmpty(memberId) && StringUtils.isNotEmpty(token) && type != null) {
            MemberToken memberToken = new MemberToken();
            if (type.getValue().equals(MemberTokenType.TOKEN_IOS.getValue())) {
                memberToken.setIosToken(token);
            }
            if (type.getValue().equals(MemberTokenType.TOKEN_ANDROID.getValue())) {
                memberToken.setAndroidToken(token);
            }


            // ture 存在会员id 更新
            if (checkExistMemberIdMemberToken(memberId)) {
                MemberTokenBean memberTokenBean = getMemberToken(memberId);
                memberToken.setId(memberTokenBean.getId());
                memberToken.setUpdateTime(new Date());
                memberTokenMapper.updateByPrimaryKeySelective(memberToken);
            } else {
                // 添加
                memberToken.setMemberId(memberId);
                Date m_date = new Date();
                memberToken.setCreateTime(m_date);
                memberToken.setUpdateTime(m_date);
                memberTokenMapper.insertSelective(memberToken);
            }

        }
    }


    public boolean checkExistMemberIdMemberToken(String memberId) {
        if (StringUtils.isNotEmpty(memberId)) {
            MemberTokenExample m_example = new MemberTokenExample();
            MemberTokenExample.Criteria m_criteria = m_example.createCriteria();
            m_criteria.andMemberIdEqualTo(memberId);
            return memberTokenMapper.countByExample(m_example) > 0;
        }
        return false;
    }

    @Override
    public MemberTokenBean getMemberToken(String memberId) {

        if (StringUtils.isNotEmpty(memberId)) {
            MemberTokenExample m_memberTokenExample = new MemberTokenExample();
            MemberTokenExample.Criteria m_criteria = m_memberTokenExample.createCriteria();
            m_criteria.andMemberIdEqualTo(memberId);
            List<MemberToken> m_list = memberTokenMapper.selectByExample(m_memberTokenExample);
            if (CollectionUtils.isNotEmpty(m_list)) {
                return BeanUtil.getBean(m_list.get(0), MemberTokenBean.class);
            }
        }
        return null;
    }

    @Override
    public void updateCheckPoint(User user) {


        userMapper.updateByPrimaryKeySelective(user);

    }


}
