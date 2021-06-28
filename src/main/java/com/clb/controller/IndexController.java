package com.clb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Controller
public class IndexController extends BaseController {







    @RequestMapping("/addauth")
    public String addAuthority() {
        String authName;
        authName = "adduser";
        return "html/user/addauth";
    }
    @RequestMapping("/river")
    public ModelAndView test() {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("html/river");
        return modelAndView;
    }
   /* @RequestMapping("/getriver")
    public String getRiver(Model model) {
        List<River> riverDaoList = riverService.getRiverList();
        model.addAttribute("message",riverDaoList);
        return "river";
    }*/

    @RequestMapping("/index")
    public String index(HttpServletRequest request) {


        return "html/index";
    }


    @RequestMapping("/river/proxy")
    public String proxy(HttpServletRequest request) {


        return "proxy";
    }











}
