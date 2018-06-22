package com.xuyazhou.mynote.model.event;

/**
 * Author: lampard_xu(xuyazhou18@gmail.com)
 * *
 * Date: 2017/5/2
 */
public class UserAuthSuccessEvent {

    String token;

    public UserAuthSuccessEvent() {

    }

    public UserAuthSuccessEvent(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }


}
