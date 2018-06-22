package com.xuyazhou.mynote.common.config;

/**
 * 网络错误码
 * Author: lampard_xu(xuyazhou18@gmail.com)
 *
 * Date: 2017-06-21
 */
public class ErrorCode {

    //Token已过期(用户半个小时没有操作系统token就会丢失，需重新登录)
    public static final String TOKENEXPIRED = "M00000";
    //Token不存在或已过期
    public static final String TOKENEXPIRED2 = "M00009";
    //当前接口访问无权限
    public static final String TOKENEXPIRED3 = "L01001";
    //绑卡失败
    public static final String BindCardFail = "M0006";
    //支付密码错误
    public static final String PayWordFail = "O0019";
}
