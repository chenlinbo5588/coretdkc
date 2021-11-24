package com.clb.config;



public class AppConsts {
    public static final String MD5 = "MD5";
    public static final String AES = "AES";
    public static final String DES = "DES";
    public static final String RSA = "RSA";
    public static final String SHA = "SHA";
    public static final String UTF8 = "UTF-8";
    public static final String SSOLOGIN_TICKET_VALIDATE_URL;

    public AppConsts() {
    }

    static {
        SSOLOGIN_TICKET_VALIDATE_URL = AppConfig.WATERMANAGE_AUTH_BASEURL + "/api/ssologin/validateTicket";
    }
}
