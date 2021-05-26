package com.clb.dao;

import com.clb.entity.User;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserMapper {

    @Select("select * from user where id =#{id}")
    List<User> getUserById(int id);

    @Select("select * from user where username = #{name}")
    User getUserByName(String name);

}
