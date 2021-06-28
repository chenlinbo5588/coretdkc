package com.clb.controller;

import com.clb.entity.River;
import com.clb.entity.RiverType;
import com.clb.service.RiverService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
@RequestMapping("/river")
@Controller
public class RiverController extends BaseController {

    @Resource
    RiverService riverService;

    @RequestMapping("/manage")
    public String manage(HttpServletRequest request, ModelMap map) {


        String value = request.getParameter("value");
        String[] typeList = null;
        String type = request.getParameter("type");
        ArrayList<RiverType> riverTypes = new ArrayList<>();
        RiverType riverType =new RiverType("河道","0");
        riverTypes.add(riverType);
        riverType =new RiverType("山塘","0");
        riverTypes.add(riverType);
        riverType =new RiverType("水库","0");
        riverTypes.add(riverType);
        riverType =new RiverType("其他水域","0");
        riverTypes.add(riverType);
        if(type !=null){
            typeList = type.split(",");
            for(int i = 0;i<typeList.length;i++){
                for(int n =0;n<riverTypes.size();n++){
                    if(typeList[i].equals(riverTypes.get(n).getType()) ){
                        riverTypes.get(n).setSelect("1");
                    }
                }
            }
        }
        map.put("riverTypes",riverTypes);
        if(value !=null){
            map.put("value",value);

            if(type != ""){
                List<River> riverList = riverService.getByLikeNameAndTypes(value,typeList);
                map.put("riverList",riverList);
            }else{
                List<River> riverList = riverService.getByLikeName(value);
                map.put("riverList",riverList);
            }
        }else{
            map.put("value","");
            map.put("riverList",null);
        }


        return "html/river/manage";
    }

    @RequestMapping("/indexSearch")
    public String indexSearch(HttpServletRequest request, ModelMap map) {
        String value = request.getParameter("value");

        if(value !=null){
            map.put("value",value);
            List<River> riverList = riverService.getByLikeName(value);
            map.put("list",riverList);
        }else{
            map.put("value",null);
            map.put("list",null);
        }


        return "html/river/list";
    }











}
