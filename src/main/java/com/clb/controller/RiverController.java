package com.clb.controller;

import com.clb.entity.*;
import com.clb.service.RiverService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
@RequestMapping("/river")
@Controller
public class RiverController extends BaseController {

    @Resource
    RiverService riverService;



    @RequestMapping("/indexSearch")
    public String indexSearch(HttpServletRequest request, ModelMap map) {
        String value = request.getParameter("value");

        if(value !=null){
            map.put("value",value);
            List<SyRvaa> riverList = riverService.getByLikeName(value);
            map.put("list",riverList);
        }else{
            map.put("value",null);
            map.put("list",null);
        }


        return "html/river/list";
    }
    @RequestMapping("/waterInfo")
    public String waterInfo(@RequestParam(value = "id", required = false,defaultValue = "0") int id, HttpServletRequest request, ModelMap map) {

        SyRvaa syRvaa = riverService.getById(id);
        map.put("data",syRvaa);

        return "html/river/info";
    }

    @RequestMapping("/changeSelect")
    public String changeSelect(HttpServletRequest request, ModelMap map) {



        return "html/river/changeSelect";
    }

    @RequestMapping("/baobiao")
    public String baobiao(@RequestParam(value = "type", required = false, defaultValue = "河道") String type,
                          HttpServletRequest request, ModelMap map) throws InvocationTargetException, IllegalAccessException {

        List<WaterTj> typeList = riverService.getTypeList();
        map.put("typeList",typeList);
        List<Tubiao> data = riverService.getTubiaoList(type);
        map.put("data",data);
        map.put("type",type);
        return "html/river/baobiao";
    }











}
