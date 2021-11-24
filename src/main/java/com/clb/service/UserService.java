package com.clb.service;

import com.clb.entity.SyRvaa;
import com.clb.entity.SyUser;

import java.util.List;

public interface UserService {

    public Boolean isUser(String name);

    public SyUser getUser(String userCode,String mobile,String trueName);

    public void saveUser(SyUser syUser);

}
