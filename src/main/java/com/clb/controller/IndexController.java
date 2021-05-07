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
public class IndexController extends BaseController {

    @Resource
    private RiverService riverService;

    @RequestMapping("/river")
    public ModelAndView test() {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("html/river");
        return modelAndView;
    }
    @RequestMapping("/getriver")
    public String getRiver(Model model) {
        List<River> riverDaoList = riverService.getRiverList();
        model.addAttribute("message",riverDaoList);
        return "river";
    }




    @RequestMapping("/proxy")
    public String proxy(HttpServletRequest request) {
        System.out.println(request.getMethod());
        return "proxy";
    }











}
