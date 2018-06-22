package com.xuyazhou.mynote.model.bean;

import com.xuyazhou.mynote.model.User;

/**
 * Author: lampard_xu(xuyazhou18@gmail.com)
 * *
 * Date: 2017-04-06
 */
public class UserbeanBack {

    private User userBean;
    private String token;

    public UserbeanBack(User user, String token) {
        this.userBean = user;
        this.token = token;
    }

    public User getUser() {
        return userBean;
    }

    public void setUser(User userBean) {
        this.userBean = userBean;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
