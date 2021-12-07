package com.clb.interceptor;

import com.clb.componet.UserDao;
import com.clb.entity.SyUser;
import com.clb.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
@Component
public class WebAppInterceptor implements HandlerInterceptor {

    @Autowired
    private UserService userService;

    @Value("${sptUrl}")
    private String sptUrl;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("webapp request 拦截器 preHandle");

        HttpSession session = request.getSession();


        Integer outPuttime = (Integer) session.getAttribute("loginTime");
        if (outPuttime != null && outPuttime > Math.round(System.currentTimeMillis() / 1000)) {

        } else {
            String userCode = (String) session.getAttribute("userCode");
            String loginName = (String) session.getAttribute("loginName");
            String mobile = (String) session.getAttribute("mobile");
            String trueName = (String) session.getAttribute("trueName");

            if (userCode == null && loginName == null && mobile == null && trueName == null) {
                response.sendRedirect(sptUrl);
            } else {
                SyUser syUser = userService.getUser(userCode, mobile, trueName);
                if (syUser == null) {
                    response.sendRedirect(sptUrl);
                } else {
                    if (syUser.getExpirationTime() < Math.round(System.currentTimeMillis() / 1000)) {
                        response.sendRedirect(sptUrl);
                    } else {
                        int time = Math.round(System.currentTimeMillis() / 1000);
                        if (syUser.getExpirationTime() - Math.round(System.currentTimeMillis() / 1000) > 60 * 60) {
                            session.setAttribute("loginTime", time + 60 * 60);
                        } else {
                            session.setAttribute("loginTime", syUser.getExpirationTime());
                        }
                    }
                }
            }
        }
        return true;

    }

//    @Override
//    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//
//
//        log.info("webapp request 拦截器 postHandle");
//    }
//
//    @Override
//    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//
//        log.info("webapp request 拦截器 完成");
//    }
}
