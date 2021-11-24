
package com.clb.controller;

import com.clb.common.SSOSdk;
import com.clb.constant.DateConstant;
import com.clb.entity.SyUser;
import com.clb.service.RiverService;
import com.clb.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * sso单点登录接口示例，仅供参考
 *
 * @date 2019/8/7
 */


@Controller
public class SSOController {

    Logger logger = LoggerFactory.getLogger(SSOController.class);

    @Value("${appkey}")
    private String appkey;

    @Value("${appsecret}")
    private String appsecret;
    @Value("${ticketValidateUrl}")
    private String ticketValidateUrl;

    @Autowired
    UserService userService;


    @RequestMapping("/ssoTokenChec1k")
    public String validateTicket(String ticket, HttpServletRequest request,HttpServletResponse response) throws Exception {

        // 默认从配置文件中读取
        SSOSdk ssoSdk = new SSOSdk();

        //TODO 获取到水管平台生成的临时票据 ticket，此处作为示例
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
//        SyUser syUser =userService.getUser(userCode,mobile,trueName,loginName);
//
//        long timestamp = Math.round(System.currentTimeMillis()/1000);
//
//        int expirationTime = (int) (DateConstant.HALF_DAY_SECONDS+timestamp);
//        if(syUser !=null){
//            syUser.setExpirationTime(expirationTime);
//        }else{
//            syUser =new SyUser();
//            syUser.setUserCode(userCode);
//            syUser.setLoginName(loginName);
//            syUser.setMobile(mobile);
//            syUser.setTrueName(trueName);
//            syUser.setExpirationTime(expirationTime);
//        }
//        userService.saveUser(syUser);
//
//        HttpSession session = request.getSession();
//
//        session.setAttribute("userCode", userCode);
//        session.setAttribute("loginName", loginName);
//        session.setAttribute("mobile", mobile);
//        session.setAttribute("trueName", trueName);


        return "redirect:/index";
        //TODO 得到水管平台返回的用户信息后，业务系统可根据实际需求进行下一步处理
        //TODO 可以通过比对本地用户数据与水管返回的用户数据，如果没有对应的用户数据则可以向水管发起请求并获取到对应的用户数据

    }

    /**
     * @param ticket 水管平台生成的单点登录票据
     * @return java.lang.String
     * @Description 验证水管平台的票据信息，返回验证得到的用户信息
     * @date 2019/8/8 10:54
     **/
    @RequestMapping("/ssoTokenCheck")
    public String validateTicketByCustomConfig(String ticket, HttpServletRequest request,HttpServletResponse response) throws Exception {

        String appkey = this.appkey;
        String appsecret = this.appsecret;
        String ticketValidateUrl = this.ticketValidateUrl;
        SSOSdk ssoSdk = new SSOSdk(appkey, appsecret, ticketValidateUrl);

        Map<String, Object> ssoUserMap = ssoSdk.validateSsoTicket(ticket);

        String userCode = (String) ssoUserMap.get("userCode");
        String loginName = (String) ssoUserMap.get("loginName");
        String mobile = (String) ssoUserMap.get("mobile");
        String trueName = (String) ssoUserMap.get("trueName");
        logger.info("userCode:{},trueName:{},loginName:{},mobile:{}", userCode, trueName, loginName, mobile);

        SyUser syUser =userService.getUser(userCode,mobile,trueName);

        long timestamp = Math.round(System.currentTimeMillis()/1000);

        int expirationTime = (int) (DateConstant.HALF_DAY_SECONDS+timestamp);
        if(syUser !=null){
            syUser.setExpirationTime(expirationTime);
        }else{
            syUser =new SyUser();
            syUser.setUserCode(userCode);
            syUser.setLoginName(loginName);
            syUser.setMobile(mobile);
            syUser.setTrueName(trueName);
            syUser.setExpirationTime(expirationTime);
        }

        userService.saveUser(syUser);

        HttpSession session = request.getSession();

        session.setAttribute("userCode", userCode);
        session.setAttribute("loginName", loginName);
        session.setAttribute("mobile", mobile);
        session.setAttribute("trueName", trueName);
        session.setAttribute("expirationTime", expirationTime);

        return "redirect:/index";

        //TODO 得到水管平台返回的用户信息后，业务系统可根据实际需求进行下一步处理
        //TODO 可以通过比对本地用户数据与水管返回的用户数据，如果没有对应的用户数据则可以向水管发起请求并获取到对应的用户数据

    }


}
