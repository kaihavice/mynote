package com.xuyazhou.mynote.model.bean;

/**
 * Author: lampard_xu(xuyazhou18@gmail.com)
 * <p>
 * Date: 2016-06-12
 */

public class LoginBean {


    /**
     * randomToken : a18bfb12310143f299914ac00ca3c5b4
     * nonce : aea6d503362ebd1c8ff6e04235f6223a
     */

    private String randomToken;
    private String nonce;

    public String getRandomToken() {
        return randomToken;
    }

    public void setRandomToken(String randomToken) {
        this.randomToken = randomToken;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }
}
