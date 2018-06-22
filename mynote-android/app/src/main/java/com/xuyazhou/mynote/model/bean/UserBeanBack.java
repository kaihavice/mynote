package com.xuyazhou.mynote.model.bean;

import com.xuyazhou.mynote.model.db.User;

/**
 * Author: lampard_xu(xuyazhou18@gmail.com)
 * *
 * Date: 2017/4/27
 */
public class UserBeanBack {

    /**
     * userBean : {"id":"910000173","userName":null,"password":"xyz501810074","accessToken":null,"ceatedTime":1493279267080,"modifyTime":1493279267080,"lastSyncTime":null,"avatar":"","mobilePhone":"","status":0,"email":"xuyazhou18@gmail.com"}
     * token : 801c660757bf4c568e122933ad1d7f3e
     */

    private User user;
    private String token;

    public User getUserBean() {
        return user;
    }

    public void setUserBean(User userBean) {
        this.user = userBean;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


}
