package com.clb.dao;

import com.clb.entity.UserDO;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends BaseDao<UserDO> {


    /**
     * 账号注册校验
     *
     * @param userDO 用户信息
     * @return
     */
    int signUpCheck(UserDO userDO);



}
