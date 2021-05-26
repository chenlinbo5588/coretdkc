package com.clb.service;

import com.clb.common.ApiResult;
import com.clb.common.userlogin.ApiResultI18n;
import com.clb.UserSignUpBean;

import java.util.Map;

public interface UserService {

    /**
     * 列表查询
     *
     * @param map 分页信息
     * @return
     */
    ApiResult queryList(Map<String, Object> map);

    /**
     * 用户注册
     *
     * @param userSignUpBean 用户注册信息
     * @return
     */
    ApiResultI18n signUp(UserSignUpBean userSignUpBean) throws Exception;

}
