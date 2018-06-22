package com.xuyazhou.mynote.service.impl;

import com.xuyazhou.mynote.Utils.MD5Util;
import com.xuyazhou.mynote.exception.MemberException;
import com.xuyazhou.mynote.handler.IMemberHandler;
import com.xuyazhou.mynote.model.User;
import com.xuyazhou.mynote.model.bean.UserStatusType;
import com.xuyazhou.mynote.model.bean.UserbeanBack;
import com.xuyazhou.mynote.service.IMemberLoginRegistService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Author: lampard_xu(xuyazhou18@gmail.com)
 * *
 * Date: 2017-04-11
 */
@Service
public class MemberLoginRegistService implements IMemberLoginRegistService {

    private final Logger logger = LoggerFactory.getLogger(getClass());


    private static final int NONCE_TOKEN_EXPIRED_TIME = 60 * 60;

    private static final int SESSION_EXPIRED_TIME = 60 * 60 * 24 * 3;

    private static final String RANDOM_TOKEN = "randomToken";

    private static final String NONCE = "nonce";

    @Autowired
    private IMemberHandler memberHandler;

    @Autowired
    @Qualifier("redisTemplate")
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    @Qualifier("stringRedisTemplate")
    private RedisTemplate<String, String> stringRedisTemplate;

    private Random random = new Random();

    @Override
    public UserbeanBack regist(String email, String password) throws MemberException {
        // 账号密码不能为空
        if (StringUtils.isBlank(email) || StringUtils.isBlank(password)) {
            logger.error("账号密码不能为空");
            throw new MemberException(MemberException.ISNOTBLANK_ERROR);
        }

        // 初始化用户信息
        User userBean = new User();
        userBean.setSid(String.valueOf(Math.abs(random.nextInt(10))) + memberHandler.getseq("user")
                + String.valueOf(Math.abs(random.nextInt(10))));
        userBean.setEmail(email);
        userBean.setPassword("");
        userBean.setCreatedTime(new Date());
        userBean.setModifyTime(new Date());
        userBean.setAvatar("");
        userBean.setUserName("");
        userBean.setMobilePhone("");
        userBean.setSyncnotetime(new Date());
        userBean.setStatus(UserStatusType.NORMAL.getValue());
        memberHandler.saveMember(userBean, DigestUtils.md5Hex(password));

        String token = registerlogin(userBean.getSid());

        userBean.setAccessToken(token);

        return new UserbeanBack(userBean, token);
    }

    @Override
    public UserbeanBack login(String randomToken, String email, String password) throws MemberException {

        if (!memberHandler.existEmail(email)) {
            throw new MemberException(MemberException.ACCOUT_ISNOT_ESIST, new Throwable("账户未注册"));
        }

        // 根据loginName拿用户信息
        String m_password = memberHandler.getPassWordByemail(email);
        // loginName、nonce、requestURI MD5 加密后的密码比对
        String nonce = stringRedisTemplate.opsForValue().get(randomToken);
        if (StringUtils.isEmpty(nonce)) {
            logger.error("token 不存在或者已过期");
            throw new MemberException(MemberException.TOKEN_ERROR, new Throwable("token 不存在或者已过期"));
        }

        String md5Password = generateMD5Password(email, m_password, nonce).toLowerCase();
        System.out.println("md5Password =" + md5Password);
        if (!md5Password.equals(password == null ? null : password.toLowerCase())) {
            logger.error("帐号密码不匹配");
            throw new MemberException(MemberException.PASSWORD_ERROR, new Throwable("密码错误"));

        }

        // 删除已用token
        redisTemplate.delete(randomToken);


        // 登录成功、redis存储
        User userBean = memberHandler.getUersByemail(email);

        // 判断手机是否再次登录
        String access_token = stringRedisTemplate.opsForValue().get(userBean.getSid());
        if (!StringUtils.isBlank(access_token)) { // 再次登录踢掉上一次登录的accesstoken
            stringRedisTemplate.delete(access_token);
        }

        String m_accessToken = UUID.randomUUID().toString().replaceAll("-", "");

        stringRedisTemplate.opsForValue().set(userBean.getSid(), m_accessToken);

        stringRedisTemplate.opsForValue().set(m_accessToken, userBean.getSid());
        stringRedisTemplate.expire(userBean.getSid(), SESSION_EXPIRED_TIME, TimeUnit.SECONDS);
        stringRedisTemplate.expire(m_accessToken, SESSION_EXPIRED_TIME, TimeUnit.SECONDS);
        userBean.setAccessToken(m_accessToken);
        return new UserbeanBack(userBean, m_accessToken);
    }


    private String generateMD5Password(String email, String md5Password, String nonce) {
        StringBuilder sb = new StringBuilder();

        sb.append(email).append(":").append(md5Password).append(":").append(nonce);

        return DigestUtils.md5Hex(sb.toString());
    }

    @Override
    public String registerlogin(String id) throws MemberException {

        String accessToken = UUID.randomUUID().toString().replaceAll("-", "");

        stringRedisTemplate.opsForValue().set(id, accessToken);

        stringRedisTemplate.opsForValue().set(accessToken, id);

        stringRedisTemplate.expire(id, SESSION_EXPIRED_TIME, TimeUnit.SECONDS);
        stringRedisTemplate.expire(accessToken, SESSION_EXPIRED_TIME, TimeUnit.SECONDS);

        return accessToken;
    }

    @Override
    public String getRandomToken(String p_Sid) {
        String p_Token = UUID.randomUUID().toString().replaceAll("-", "");
        String p_MDToken = MD5Util.MD5(p_Token);
        redisTemplate.opsForValue().set(p_Sid, p_MDToken);
        redisTemplate.expire(p_Sid, 60 * 10, TimeUnit.SECONDS);
        return p_MDToken;
    }

    @Override
    public String getuserIdbyaccesstoken(String p_accessToken) throws MemberException {
        String userId = stringRedisTemplate.opsForValue().get(p_accessToken);
        if (StringUtils.isBlank(userId)) {
            throw new MemberException(MemberException.ACCESSTOKEN_ERROR);
        }
        return userId;
    }

    @Override
    public Map<String, String> getNonce() {
        Map<String, String> m_results = new HashMap<>();
        // 生成一个新的随机临时token
        String randomToken = UUID.randomUUID().toString().replaceAll("-", "");
        m_results.put(RANDOM_TOKEN, randomToken);

        String m_nonce = nonce();
        m_results.put(NONCE, m_nonce);

        stringRedisTemplate.opsForValue().set(randomToken, m_nonce);
        stringRedisTemplate.expire(randomToken, NONCE_TOKEN_EXPIRED_TIME, TimeUnit.SECONDS);
        return m_results;
    }

    @Override
    public void logout(String token) {
        stringRedisTemplate.delete(token);
    }

    private String nonce() {
        List<String> m_nonce = Arrays.asList(UUID.randomUUID().toString().replaceAll("-", "").split(""));
        Collections.shuffle(m_nonce);
        return StringUtils.join(m_nonce.iterator(), "");
    }

    @Override
    public String checkLogin(String p_accessToken) {
        logger.info("check login accessToken==================" + p_accessToken);
        String m_memberId = stringRedisTemplate.opsForValue().get(p_accessToken);
        logger.info("check login m_memberId==================" + m_memberId);
        if (StringUtils.isNotEmpty(m_memberId)) {
            stringRedisTemplate.expire(p_accessToken, SESSION_EXPIRED_TIME, TimeUnit.SECONDS);
            stringRedisTemplate.expire(m_memberId, SESSION_EXPIRED_TIME, TimeUnit.SECONDS);
            return m_memberId;
        }
        return "";
    }
}
