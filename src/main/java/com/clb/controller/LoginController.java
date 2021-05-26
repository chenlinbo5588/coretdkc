package com.clb.controller;

import com.clb.dao.UserMapper;
import com.clb.entity.River;
import com.clb.entity.User;
import com.clb.service.RiverService;
import com.clb.service.UserService;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class LoginController extends BaseController {

    @Resource
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @RequestMapping("/login/{id}")
    public String Login(@PathVariable("id") Integer id) {

        //User user =userService.getUserById(id);




        return "123";
    }

}
