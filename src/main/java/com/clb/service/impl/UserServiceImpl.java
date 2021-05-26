package com.clb.service.impl;

import com.clb.UserSignUpBean;
import com.clb.common.ApiResult;
import com.clb.common.userlogin.ApiResultI18n;
import com.clb.common.userlogin.ResponseCodeI18n;
import com.clb.dao.UserDao;
import com.clb.entity.UserDO;
import com.clb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    /**
     * 列表查询
     *
     * @param map 分页信息
     * @return
     */
    @Override
    public ApiResult queryList(Map<String, Object> map) {

        // TODO 分页数据处理
        List<Object> list = userDao.queryListComplex(map);

        return ApiResult.success(userDao.queryListComplex(map));
    }

    /**
     * 用户注册
     *
     * @param userSignUpBean 用户注册信息
     * @return
     */
    @Override
    public ApiResultI18n signUp(UserSignUpBean userSignUpBean) throws Exception{
        /**
         * 参数校验顺序: 基本入参校验 --> 具体参数合法性校验(非数据库层校验) --> 数据库层参数校验
         */

        /**
         * 请求参数获取
         */
        UserDO userParams = new UserDO();
        /**
         * 此处省略
         * 参数合法性校验校验... ...
         */

        // 注册帐号数据库层校验
        int userCount = userDao.signUpCheck(userParams);
        if (userCount > 0) {
            return ApiResultI18n.failure(ResponseCodeI18n.ACCOUNT_EXIST, userSignUpBean.getLanguage());
        }
        /**
         * 此处省略
         * 用户注册相关操作... ...
         */

        return ApiResultI18n.success(userSignUpBean.getLanguage());
    }


}

