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

    @RequestMapping("/jsp")
    public String jsp(Model model) {
        model.addAttribute("message", "this is index jsp page!");
        return "hello";
    }

    @RequestMapping("/html")
    public String testThemleaf(Model model) {
        model.addAttribute("message", "this is index html page!");
        return "html/river";
    }
    @RequestMapping("/proxy")
    public String proxy(HttpServletRequest request) {

        return "proxy";
    }











}