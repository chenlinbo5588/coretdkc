///**
// * Copyright (C), 2019, 杭州定川信息技术有限公司
// * FileName: CallBackController
// * Author:   MJ
// * Date:     2019/10/29 14:44
// * Description: 事件回调服务接口
// * History:
// * <author>          <time>          <version>          <desc>
// * 作者姓名           修改时间           版本号              描述
// */
//package com.clb.controller;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import com.dcxx.framework.watermanagesdk.config.CallBackConfig;
//import com.dcxx.framework.watermanagesdk.consts.CallBackConsts;
//import com.dcxx.framework.watermanagesdk.utils.UtilsHelper;
//import com.dcxx.framework.watermanagesdk.utils.WaterManageEncryptException;
//import com.dcxx.framework.watermanagesdk.utils.WaterManageEncryptor;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.web.bind.annotation.*;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.*;
//
///**
// * 事件回调服务接口示例，仅供参考
// *
// * @date 2019/10/29
// */
//@RestController
//@RequestMapping("/callback")
//public class CallBackController {
//
//    //日志对象
//    Logger logger = LoggerFactory.getLogger(CallBackController.class);
//
//    /**
//     * 事件回调处理接口，仅供参考，异常请自行处理
//     *
//     * @param json 水管平台回调信息
//     * @return java.util.Map<java.lang.String, java.lang.String>  需要给水管平台响应的消息内容
//     * @date 2020/03/15 14:48
//     **/
//    @PostMapping("/index")
//    public Map<String, Object> index(@RequestBody(required = true) JSONObject json, HttpServletRequest request) throws WaterManageEncryptException, JsonProcessingException {
//
//        logger.debug("推送数据==>{}", json);
//
//        String signature = json.getString("signature");
//        String timestamp = json.getString("timestamp");
//        String nonce = json.getString("nonce");
//        String params = " signature:" + signature + " timestamp:" + timestamp + " nonce:" + nonce + " json:" + json;
//        logger.debug("请求参数：{}", params);
//
//        StringBuffer urlBuffer = request.getRequestURL();
//        logger.debug("请求 URL :{}", urlBuffer.toString());
//
//        WaterManageEncryptor manageEncryptor = null;
//        try {
//            //构造加解密对象
//            manageEncryptor = new WaterManageEncryptor(CallBackConfig.token, CallBackConfig.encodingAESKey, CallBackConfig.appkey);
//        } catch (WaterManageEncryptException e) {
//            logger.error("事件回调配置异常", e);
//        }
//
//        //从post请求的body中获取回调信息的加密数据进行解密处理
//        String encryptMsg = json.getString("encrypt");
//
//        //获取从encrypt解密出来的明文
//        String plainText = manageEncryptor.getDecryptMsg(signature, timestamp, nonce, encryptMsg, CallBackConfig.isEncrypt);
//        JSONObject obj = JSON.parseObject(plainText);
//
//        logger.info("plainText:{}", plainText);
//        //根据回调事件类型做不同的业务处理
//        String eventType = obj.getString("eventType");
//        logger.info("eventType:{}", eventType);
//
//        String responseMsg = CallBackConsts.CALLBACK_RESPONSE_SUCCESS;
//        //check_url 事件回调
//        switch (eventType) {
//            // TODO 注册回调事件
//            case "check_url":
//                break;
//            // TODO 用户事件处理，demo
//            case "org_user_add":
//                // case "org_user_modify":
//                // case "org_user_delete":
//                handlerUserEvent(obj);
//                break;
//            //TODO 其他类型事件参考开发文档处理
//        }
//
//        // 返回给水管平台的响应消息
//        Map<String, Object> responseData = manageEncryptor.getEncryptedMap(responseMsg, System.currentTimeMillis(), UtilsHelper.genernateNonce(8), CallBackConfig.isEncrypt);
//        return responseData;
//    }
//
//    /**
//     * 用户推送事件解析，仅供参考
//     *
//     * @param obj 消息体对象
//     * @date 2020/3/19 15:58
//     **/
//    private void handlerUserEvent(JSONObject obj) {
//
//        JSONArray idArray = obj.getJSONArray("id");
//        // 根据水管的推送ID进行业务处理,如检查用户是否存在等
//        for (Object id : idArray) {
//
//            // TODO 获取到水管推送的用户编码
//            String userCode = id.toString();
//
//            //TODO 业务数据处理 balabala
//        }
//
//    }
//
//
//}
