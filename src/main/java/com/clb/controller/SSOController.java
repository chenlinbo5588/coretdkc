///**
// * Copyright (C), 2019, 杭州定川信息技术有限公司
// * FileName: SSOController
// * Author:   MJ
// * Date:     2019/8/7 18:30
// * Description: sso单点登录接口
// * History:
// * <author>          <time>          <version>          <desc>
// * 作者姓名           修改时间           版本号              描述
// */
//package com.clb.controller;
//
//import com.dcxx.framework.watermanagesdk.SSOSdk;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.Map;
//
///**
// * sso单点登录接口示例，仅供参考
// *
// * @date 2019/8/7
// */
//
//
//@RestController
//@RequestMapping("/sso")
//public class SSOController {
//
//    Logger logger = LoggerFactory.getLogger(SSOController.class);
//
//    /**
//     * @param ticket 水管平台生成的单点登录票据
//     * @return java.lang.String
//     * @Description 验证水管平台的票据信息，返回验证得到的用户信息
//     * @date 2019/8/8 10:54
//     **/
//    @RequestMapping("/validateTicket")
//    public void validateTicket(String ticket) throws Exception {
//
//        // 默认从配置文件中读取
//        SSOSdk ssoSdk = new SSOSdk();
//
//        //TODO 获取到水管平台生成的临时票据 ticket，此处作为示例
//        ticket = "ticket_abcdefg12345678_test";
//        Map<String, Object> ssoUserMap = ssoSdk.validateSsoTicket(ticket);
//
//        String userCode = (String) ssoUserMap.get("userCode");
//        String loginName = (String) ssoUserMap.get("loginName");
//        String mobile = (String) ssoUserMap.get("mobile");
//        String trueName = (String) ssoUserMap.get("trueName");
//
//        logger.info("userCode:{},trueName:{},loginName:{},mobile:{}", userCode, trueName, loginName, mobile);
//
//        //TODO 得到水管平台返回的用户信息后，业务系统可根据实际需求进行下一步处理
//        //TODO 可以通过比对本地用户数据与水管返回的用户数据，如果没有对应的用户数据则可以向水管发起请求并获取到对应的用户数据
//
//    }
//
//
//    /**
//     * @param ticket 水管平台生成的单点登录票据
//     * @return java.lang.String
//     * @Description 验证水管平台的票据信息，返回验证得到的用户信息
//     * @date 2019/8/8 10:54
//     **/
//    @RequestMapping("/validateTicketByCustomConfig")
//    public void validateTicketByCustomConfig(String ticket) throws Exception {
//
//        // 指定 appkey 和 appsecret 及票据验证URL,此 appkey 和 appsecret 仅供测试使用
//        String appkey = "e2a6205ec33511e9b83200ff5f8a2c01";
//        String appsecret = "03ecfc8cc33611e9b83200ff";
//        String ticketValidateUrl = "https://sgpt.zjwater.com/oapi/water-manage-auth/api/ssologin/validateTicket";
//        SSOSdk ssoSdk = new SSOSdk(appkey, appsecret, ticketValidateUrl);
//
//        //TODO 获取到水管平台生成的临时票据 ticket，此处作为示例
//        ticket = "ticket_abcdefg12345678_test";
//        Map<String, Object> ssoUserMap = ssoSdk.validateSsoTicket(ticket);
//
//        String userCode = (String) ssoUserMap.get("userCode");
//        String loginName = (String) ssoUserMap.get("loginName");
//        String mobile = (String) ssoUserMap.get("mobile");
//        String trueName = (String) ssoUserMap.get("trueName");
//
//        logger.info("userCode:{},trueName:{},loginName:{},mobile:{}", userCode, trueName, loginName, mobile);
//
//        //TODO 得到水管平台返回的用户信息后，业务系统可根据实际需求进行下一步处理
//        //TODO 可以通过比对本地用户数据与水管返回的用户数据，如果没有对应的用户数据则可以向水管发起请求并获取到对应的用户数据
//
//    }
//
//
//}
