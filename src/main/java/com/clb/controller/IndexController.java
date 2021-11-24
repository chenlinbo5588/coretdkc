package com.clb.controller;

import com.clb.common.SSOSdk;
import com.clb.constant.DateConstant;
import com.clb.dto.ArcgisTc;
import com.clb.entity.Project;
import com.clb.entity.SyUser;
import com.clb.service.ProjectService;
import com.clb.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
public class IndexController extends BaseController {
    Logger logger = LoggerFactory.getLogger(SSOController.class);

    @Value("${appkey}")
    private String appkey;

    @Value("${appsecret}")
    private String appsecret;
    @Value("${ticketValidateUrl}")
    private String ticketValidateUrl;

    @Autowired
    UserService userService;

    @Resource
    ProjectService projectService;

    @RequestMapping("/addauth")
    public String addAuthority() {
        String authName;
        authName = "adduser";
        return "html/user/addauth";
    }


    @RequestMapping({"/", "/index"})
    public String index(HttpServletRequest request,ModelMap map,HttpServletResponse response) {

//        List<ArcgisTc> arcgisTcs = new ArrayList<ArcgisTc>();
//
//        arcgisTcs.add(new ArcgisTc("河道","河道水域"));
//        arcgisTcs.add(new ArcgisTc("水库","水库"));
//        arcgisTcs.add(new ArcgisTc("湖泊","湖泊"));
//        arcgisTcs.add(new ArcgisTc("山塘","山塘"));
//        arcgisTcs.add(new ArcgisTc("人工水道","人工水道"));
//        arcgisTcs.add(new ArcgisTc("其他水域","其他水域"));
//        map.put("data",arcgisTcs);
        List<Project> project= projectService.getOverTimeProject();

        HttpSession session = request.getSession();
        String trueName = (String) session.getAttribute("trueName");

        map.put("trueName",trueName);
        map.put("projectList",project);
        return "html/index";
    }



    @RequestMapping("/test")
    public String test(ModelMap map)  {
      return "html/river/test";
    }
    @RequestMapping("/river/proxy")
    public void  proxy(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.getRequestDispatcher("/jsp/proxy.jsp").forward(request,response);

    }

    @RequestMapping("/tools/getriverImg")
    public String get(@RequestParam(value = "x", required = true) float x,@RequestParam(value = "y", required = true) float y,@RequestParam(value = "flag", required = false) String flag,ModelMap map) {

        //flag 权限判断
        map.put("x",x);
        map.put("y",y);



        return "html/tools/riverImg";
    }











}
