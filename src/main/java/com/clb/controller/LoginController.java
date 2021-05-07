package com.clb.controller;

import com.clb.entity.River;
import com.clb.service.RiverService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/login")
public class LoginController extends BaseController {

    @Resource
    private RiverService riverService;

    @RequestMapping("/index")
    public String Login() {


        return "html/login/index";
    }

}
