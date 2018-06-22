package com.xuyazhou.mynote.common.utils;

import java.security.MessageDigest;

/**
 * MD5工具
 * Author: lampard_xu(xuyazhou18@gmail.com)
 * <p>
 * Date: 2015-07-25
 */
public class MD5Utils {

    // MD5加密。32位
    public static String MD5(String inStr) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }
        char[] charArray = inStr.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++)
            byteArray[i] = (byte) charArray[i];

        byte[] md5Bytes = md5.digest(byteArray);

        StringBuffer hexValue = new StringBuffer();

        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16)
                hexValue.append("0");
            hexValue.append(Integer.toHexString(val));
        }

        return hexValue.toString();
    }

    /**
     * 16位MD5
     */
    public static String ToMd5U16(String str){
        String reStr = MD5(str);
        reStr = reStr.toUpperCase().substring(8, 24);
        return reStr;
    }




}
