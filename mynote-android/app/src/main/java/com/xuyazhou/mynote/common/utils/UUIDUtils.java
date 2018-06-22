package com.xuyazhou.mynote.common.utils;

import android.util.Base64;

import java.nio.ByteBuffer;
import java.util.Random;
import java.util.UUID;

/**
 * Author: lampard_xu(xuyazhou18@gmail.com)
 * *
 * Date: 2017/1/4
 */
public class UUIDUtils {


    private static String str = "aAbBcCdEfFgGhHiIjJkKlLmMnNoOpPqQrRsStTuUvVwWxXyYzZ0123456789";
    private static Random random = new Random();

    public static String getUUID() {

        UUID uuid = UUID.fromString(UUID.randomUUID().toString());
        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());
        return (str.charAt(random.nextInt(60)) +
                Base64.encodeToString(bb.array(), Base64.URL_SAFE).replaceAll("[^a-zA-Z0-9]", "") +
                str.charAt(random.nextInt(60))).toLowerCase();
    }

    public static String uuidFromBase64(String str) {
        byte[] bytes = Base64.decode(str, Base64.DEFAULT);
        ByteBuffer bb = ByteBuffer.wrap(bytes);
        UUID uuid = new UUID(bb.getLong(), bb.getLong());
        return uuid.toString();
    }
}
