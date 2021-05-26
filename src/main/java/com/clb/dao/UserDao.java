package com.clb.dao;

import com.clb.entity.River;
import com.clb.entity.User;
import com.clb.entity.UserDO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao extends BaseDao<UserDO>{

    int signUpCheck(UserDO userDO);

    public User getUserById(int id);

    public User getUserByName(String name);

}

