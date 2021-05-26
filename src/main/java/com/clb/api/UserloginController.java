package com.clb.api;

import com.clb.common.userlogin.ApiResultI18n;
import com.clb.common.userlogin.ResponseCodeI18n;
import com.clb.common.exception.ParamsCheckException;
import com.clb.service.UserService;
import com.clb.UserSignUpBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("api/user")
public class UserloginController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;


    /**
     * 用户注册
     *
     * @return
     */
    @RequestMapping(value = "signup", method = RequestMethod.GET)
    public ApiResultI18n signUp(){
        UserSignUpBean userSignUpBean = new UserSignUpBean();
        userSignUpBean.setUserPasscode("aaaaa");
        userSignUpBean.setUserName("aaaaa");
        ApiResultI18n apiResultI18n= null;
        try {
            apiResultI18n = userService.signUp(userSignUpBean);
        } catch (Exception e) {
            if (ParamsCheckException.class.isAssignableFrom(e.getClass())){
                logger.error("注册失败,参数错误");
                return apiResultI18n.failure(ResponseCodeI18n.PARAM_ERROR.getCode(), e.getMessage(),
                        userSignUpBean.getLanguage());
            }
            logger.error("注册失败,未知异常",e);
            return apiResultI18n.failure(ResponseCodeI18n.UNKNOWN_ERROR, userSignUpBean.getLanguage());
        }
        return apiResultI18n;
    }

}


