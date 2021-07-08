package com.clb.controller;

import com.clb.entity.ArcgisTc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
    public String index(HttpServletRequest request, ModelMap map) {

        List<ArcgisTc> arcgisTcs = new ArrayList<ArcgisTc>();

        arcgisTcs.add(new ArcgisTc("河道","河道水域"));
        arcgisTcs.add(new ArcgisTc("水库","水库"));
        arcgisTcs.add(new ArcgisTc("湖泊","湖泊"));
        arcgisTcs.add(new ArcgisTc("山塘","山塘"));
        arcgisTcs.add(new ArcgisTc("人工水道","人工水道"));
        arcgisTcs.add(new ArcgisTc("其他水域","其他水域"));

        map.put("data",arcgisTcs);


        return "html/index";
    }


    @RequestMapping("/river/proxy")
    public String proxy(HttpServletRequest request) {


        return "proxy";
    }











}
