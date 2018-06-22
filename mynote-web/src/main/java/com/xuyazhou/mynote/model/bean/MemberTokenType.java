package com.xuyazhou.mynote.model.bean;

/**
 * Author: lampard_xu(xuyazhou18@gmail.com)
 * *
 * Date: 2017-04-06
 */
public enum  MemberTokenType {

    // ios
    TOKEN_IOS("0"),
    // android
    TOKEN_ANDROID("1");

    private String status;

    MemberTokenType(String status) {
        this.status = status;
    }

    public String getValue() {
        return status;
    }

    public static MemberTokenType getMemberTokenTypeByValue(String value) {
        for (MemberTokenType memberTokenType : values()) {
            if (value != null && value.equals(memberTokenType.getValue())) {
                return memberTokenType;
            }
        }
        return null;
    }
}
