package com.clb.controller;

import com.clb.dto.ArcgisTc;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
public class IndexController extends BaseController {



    @RequestMapping("/addauth")
    public String addAuthority() {
        String authName;
        authName = "adduser";
        return "html/user/addauth";
    }


    @RequestMapping("/index")
    public String index(HttpServletRequest request,ModelMap map) {

        List<ArcgisTc> arcgisTcs = new ArrayList<ArcgisTc>();

        arcgisTcs.add(new ArcgisTc("河道","河道水域"));
        arcgisTcs.add(new ArcgisTc("水库","水库"));
        arcgisTcs.add(new ArcgisTc("湖泊","湖泊"));
        arcgisTcs.add(new ArcgisTc("山塘","山塘"));
        arcgisTcs.add(new ArcgisTc("人工水道","人工水道"));
        arcgisTcs.add(new ArcgisTc("其他水域","其他水域"));
//        map.put("host",arcgisMapHost);
//        map.put("mapServerName",appMapServerName);
        map.put("data",arcgisTcs);


        return "html/index";
    }

    @RequestMapping("/river/proxy")
    public String proxy(HttpServletRequest request) {
        return "proxy";
    }











}
