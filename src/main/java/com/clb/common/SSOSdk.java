package com.clb.common;


import com.clb.config.AppConfig;
import com.clb.config.AppConsts;
import com.clb.dto.ResponseMessage;
import com.clb.utils.EncryptHelper;
import com.clb.utils.HttpHelper;
import com.clb.utils.JsonHelper;
import com.clb.utils.UtilsHelper;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class SSOSdk {
    Logger logger = Logger.getLogger(this.getClass().getName());
    private String appkey;
    private String appsecret;
    private String ticketValidateUrl;

    public SSOSdk() {
        this.init((String)null, (String)null, (String)null);
    }

    public SSOSdk(String appkey, String appsecret) {
        this.init(appkey, appsecret, (String)null);
    }

    public SSOSdk(String appkey, String appsecret, String ticketValidateUrl) {
        this.init(appkey, appsecret, ticketValidateUrl);
    }

    public String getAppSecret() {
        return this.appsecret;
    }

    public String getSsologinTicketValidateUrl() {
        return this.ticketValidateUrl;
    }

    void init(String appkey, String appsecret, String ticketValidateUrl) {
        this.appkey = null != appkey && !appkey.isEmpty() ? appkey : AppConfig.WATERMANAGE_APPKEY;
        this.appsecret = null != appsecret && !appsecret.isEmpty() ? appsecret : AppConfig.WATERMANAGE_APPSECRET;
        this.ticketValidateUrl = null != ticketValidateUrl && !ticketValidateUrl.isEmpty() ? ticketValidateUrl : AppConsts.SSOLOGIN_TICKET_VALIDATE_URL;
        if (null != this.appkey && !this.appkey.isEmpty()) {
            if (null != this.appsecret && !this.appsecret.isEmpty()) {
                if (null == this.ticketValidateUrl || this.ticketValidateUrl.isEmpty()) {
                    throw new RuntimeException("票据验证地址url不能为空，请通过配置文件配置或者自行传入！");
                }
            } else {
                throw new RuntimeException("appsecret 应用密钥不能为空！");
            }
        } else {
            throw new RuntimeException("appkey 应用标识不能为空！");
        }
    }

    public Map<String, Object> validateSsoTicket(String ticket) throws Exception {
        String nonce = UtilsHelper.genernateNonce(8);
        long timestamp = System.currentTimeMillis();
        String sign = this.genernateSsoSign(this.getAppSecret(), nonce, ticket, timestamp);
        Map<String, Object> dictParams = new HashMap();
        dictParams.put("ticket", ticket);
        dictParams.put("sign", sign);
        dictParams.put("timestamp", timestamp);
        dictParams.put("nonce", nonce);
        System.out.println(JsonHelper.object2Json(dictParams));
        String resultJson = HttpHelper.doPost(this.getSsologinTicketValidateUrl(), dictParams);
        this.logger.info("票据验证结果 ==> " + resultJson);
        ResponseMessage responseMessage = (ResponseMessage)JsonHelper.json2Object(resultJson, ResponseMessage.class);
        if (0 == responseMessage.getStatus()) {
            Map<String, Object> map = (Map)responseMessage.getMessage();
            if (null != map.get("mobile")) {
                String mobile = map.get("mobile").toString();
                String realMobile = EncryptHelper.aesDecrypt(mobile, this.getAppSecret());
                this.logger.info("realMobile ==> " + realMobile);
                map.put("mobile", realMobile);
            }

            return map;
        } else {
            this.logger.info("票据验证异常 ==> " + responseMessage.getErrmsg());
            throw new RuntimeException("票据验证异常 ==> " + responseMessage.getErrmsg());
        }
    }

    public String genernateSsoSign(String appSecret, String nonce, String ticket, long timestamp) throws Exception {
        String content = "appsecret=" + appSecret + "&nonce=" + nonce + "&ticket=" + ticket + "&timestamp=" + timestamp;
        String sign = EncryptHelper.sha(content);
        return sign.toLowerCase();
    }

    public String genernateLoginValidateSign(String appSecret, String nonce, String encrypt, long timestamp) throws Exception {
        String content = "appsecret=" + appSecret + "&encrypt=" + encrypt + "&nonce=" + nonce + "&timestamp=" + timestamp;
        String sign = EncryptHelper.sha(content);
        return sign.toLowerCase();
    }
}