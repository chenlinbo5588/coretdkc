package com.clb.utils;


import java.util.Random;

public class UtilsHelper {
    public UtilsHelper() {
    }

    public static String genernateNonce(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();

        for(int i = 0; i < length; ++i) {
            int number = random.nextInt(62);
            sb.append(str.charAt(number));
        }

        return sb.toString();
    }

    public static byte[] int2Bytes(int count) {
        byte[] byteArr = new byte[]{(byte)(count >> 24 & 255), (byte)(count >> 16 & 255), (byte)(count >> 8 & 255), (byte)(count & 255)};
        return byteArr;
    }

    public static int bytes2int(byte[] byteArr) {
        int count = 0;

        for(int i = 0; i < 4; ++i) {
            count <<= 8;
            count |= byteArr[i] & 255;
        }

        return count;
    }
}
