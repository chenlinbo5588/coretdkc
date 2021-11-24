package com.clb.utils;


import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.util.Base64;

public class EncryptHelper {
    public EncryptHelper() {
    }

    public static String sha(String inputStr) throws Exception {
        MessageDigest sha = null;

        try {
            sha = MessageDigest.getInstance("SHA");
        } catch (Exception var7) {
            System.out.println(var7.toString());
            var7.printStackTrace();
            return "";
        }

        byte[] byteArray = inputStr.getBytes("UTF-8");
        byte[] md5Bytes = sha.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();

        for(int i = 0; i < md5Bytes.length; ++i) {
            int val = md5Bytes[i] & 255;
            if (val < 16) {
                hexValue.append("0");
            }

            hexValue.append(Integer.toHexString(val));
        }

        return hexValue.toString();
    }

    public static String aesEncrypt(String plainText, String secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        byte[] raw = secretKey.getBytes();
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        cipher.init(1, skeySpec);
        byte[] encrypted = cipher.doFinal(plainText.getBytes("UTF-8"));
        Base64.Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(encrypted);
    }

    public static String aesDecrypt(String cliperText, String secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        byte[] raw = secretKey.getBytes();
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        cipher.init(2, skeySpec);
        byte[] encrypted = Base64.getDecoder().decode(cliperText);
        byte[] original = cipher.doFinal(encrypted);
        String originalString = new String(original, "UTF-8");
        return originalString;
    }
}
