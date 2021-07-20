package com.clb.controller;

import com.clb.constant.DateConstant;
import com.clb.dto.FxData;
import com.clb.dto.FxTableData;
import com.clb.dto.Tubiao;
import com.clb.dto.WaterTj;
import com.clb.entity.*;
import com.clb.service.RiverService;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

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
            List<SyRvaa> syRvaas = new ArrayList<SyRvaa>();
            List<SyHpaa> syHpaas = new ArrayList<SyHpaa>();
            List<SyAcaa> syAcaas = new ArrayList<SyAcaa>();
            List<SyOwaa> syOwaas = new ArrayList<SyOwaa>();
            List<SyLkaa> syLkaas = new ArrayList<SyLkaa>();
            List<SyRsaa> syRsaas = new ArrayList<SyRsaa>();

            List<SyRvaa> rvList = riverService.getRvLikeNameOrBm(value);
            List<SyRsaa> rsList = riverService.getRsLikeNameOrBm(value);
            List<SyHpaa> hpList = riverService.getHpLikeNameOrBm(value);
            List<SyAcaa> acList = riverService.getAcLikeNameOrBm(value);
            List<SyOwaa> owList = riverService.getOwLikeNameOrBm(value);
            List<SyLkaa> lkList = riverService.getLkLikeNameOrBm(value);

            map.put("value",value);
            map.put("rvList",rvList);
            map.put("rsList",rsList);
            map.put("hpList",hpList);
            map.put("acList",acList);
            map.put("owList",owList);
            map.put("lkList",lkList);
        }else{
            map.put("value",null);
        }
        return "html/river/list";
    }
    @RequestMapping("/waterInfo")
    public String waterInfo(@RequestParam(value = "id", required = false,defaultValue = "0") int id,
                            @RequestParam(value = "layerId", required = false,defaultValue = "0") String layerId,
                            HttpServletRequest request, ModelMap map) {

        switch (layerId){
            case DateConstant.RV_LAYER_ID:
                SyRvaa syRvaa = riverService.getRvById(id);
                map.put("data",syRvaa);
                break;
            case  DateConstant.RS_LAYER_ID:
                SyRsaa syRsaa = riverService.getRsById(id);
                map.put("data",syRsaa);
                break;
            case  DateConstant.LK_LAYER_ID:
                SyLkaa syLkaa  =riverService.getLkById(id);
                map.put("data",syLkaa);
                break;
            case  DateConstant.HP_LAYER_ID:
                SyHpaa syHpaa  =riverService.getHpById(id);
                map.put("data",syHpaa);
                break;
            case  DateConstant.AC_LAYER_ID:
                SyAcaa syAcaa  =riverService.getAcById(id);
                map.put("data",syAcaa);
                break;
            case  DateConstant.OW_LAYER_ID:
                SyOwaa syOwaa  =riverService.getOwById(id);
                map.put("data",syOwaa);
                break;
        }
        map.put("layerId",layerId);
        return "html/river/info";
    }
    @RequestMapping("water/info/ic")
    public String waterInfoIc(@RequestParam(value = "identification", required = false) String identification,
                            @RequestParam(value = "layerId", required = false,defaultValue = "0") String layerId,
                            HttpServletRequest request, ModelMap map) {

        switch (layerId){
            case DateConstant.RV_LAYER_ID:
                SyRvaa syRvaa = riverService.getRvaaByIdentification(identification);
                map.put("data",syRvaa);
                break;
            case  DateConstant.RS_LAYER_ID:
                SyRsaa syRsaa = riverService.getRsaaByIdentification(identification);
                map.put("data",syRsaa);
                break;
            case  DateConstant.LK_LAYER_ID:
                SyLkaa syLkaa  =riverService.getLkaaByIdentification(identification);
                map.put("data",syLkaa);
                break;
            case  DateConstant.HP_LAYER_ID:
                SyHpaa syHpaa  =riverService.getHpaaByIdentification(identification);
                map.put("data",syHpaa);
                break;
            case  DateConstant.AC_LAYER_ID:
                SyAcaa syAcaa  =riverService.getAcaaByIdentification(identification);
                map.put("data",syAcaa);
                break;
            case  DateConstant.OW_LAYER_ID:
                SyOwaa syOwaa  =riverService.getOwaaByIdentification(identification);
                map.put("data",syOwaa);
                break;
        }
        map.put("layerId",layerId);
        return "html/river/info";
    }


    @RequestMapping("/changeSelect")
    public String changeSelect( ModelMap map) {

        List <ChangeWaterarea> changeWaterareas = riverService.getChangeWaterareaOrderBgdate();
        map.put("changeList",changeWaterareas);


        return "html/river/changeSelect";
    }
    @RequestMapping("/changeDetail")
    public String changeDetail( ModelMap map, @RequestParam(value = "id", required = false) int id){

        List <ChangeWaterarea> changeWaterareas = riverService.getChangeWatersById(id);
        map.put("data",changeWaterareas);


        return "html/river/changeBgDetail";
    }
    @RequestMapping("/fxSelect")
    public String fxSelect(String fxdata,ModelMap map){


        Gson gson = new Gson();
        List<FxData> list = gson.fromJson(fxdata, new TypeToken<List<FxData>>(){}.getType());
        List<SyRvaa> syRvaas = new ArrayList<SyRvaa>();
        List<SyHpaa> syHpaas = new ArrayList<SyHpaa>();
        List<SyAcaa> syAcaas = new ArrayList<SyAcaa>();
        List<SyOwaa> syOwaas = new ArrayList<SyOwaa>();
        List<SyLkaa> syLkaas = new ArrayList<SyLkaa>();
        List<SyRsaa> syRsaas = new ArrayList<SyRsaa>();

        for(int i = 0;i<list.size();i++){
           switch (list.get(i).getLayerId()){
               case  DateConstant.RV_LAYER_ID:
                   SyRvaa syRvaa  =riverService.getRvaaByIdentification(list.get(i).getIdentification());
                   if(syRvaa !=null){
                       syRvaa.setIntersectionArea(list.get(i).getArea());
                       syRvaa.setLayerId(Integer.parseInt(list.get(i).getLayerId()));
                       syRvaas.add(syRvaa);
                   }
                   break;
               case  DateConstant.RS_LAYER_ID:
                   SyRsaa syRsaa  =riverService.getRsaaByIdentification(list.get(i).getIdentification());
                   if(syRsaa !=null){
                       syRsaa.setIntersectionArea(list.get(i).getArea());
                       syRsaa.setLayerId(Integer.parseInt(list.get(i).getLayerId()));
                       syRsaas.add(syRsaa);
                   }
                   break;
               case  DateConstant.LK_LAYER_ID:
                   SyLkaa syLkaa  =riverService.getLkaaByIdentification(list.get(i).getIdentification());
                   if(syLkaa !=null){
                       syLkaa.setIntersectionArea(list.get(i).getArea());
                       syLkaa.setLayerId(Integer.parseInt(list.get(i).getLayerId()));
                       syLkaas.add(syLkaa);
                   }
                   break;
               case  DateConstant.HP_LAYER_ID:
                   SyHpaa syHpaa  =riverService.getHpaaByIdentification(list.get(i).getIdentification());
                   if(syHpaa !=null){
                       syHpaa.setIntersectionArea(list.get(i).getArea());
                       syHpaa.setLayerId(Integer.parseInt(list.get(i).getLayerId()));
                       syHpaas.add(syHpaa);
                   }
                   break;
               case  DateConstant.AC_LAYER_ID:
                   SyAcaa syAcaa  =riverService.getAcaaByIdentification(list.get(i).getIdentification());
                   if(syAcaa !=null){
                       syAcaa.setIntersectionArea(list.get(i).getArea());
                       syAcaa.setLayerId(Integer.parseInt(list.get(i).getLayerId()));
                       syAcaas.add(syAcaa);
                   }
                   break;
               case  DateConstant.OW_LAYER_ID:
                   SyOwaa syOwaa  =riverService.getOwaaByIdentification(list.get(i).getIdentification());
                   if(syOwaa !=null){
                       syOwaa.setIntersectionArea(list.get(i).getArea());
                       syOwaa.setLayerId(Integer.parseInt(list.get(i).getLayerId()));
                       syOwaas.add(syOwaa);
                   }
                   break;
           }
        }
        map.put("syRvaas",syRvaas);
        map.put("syRsaas",syRsaas);
        map.put("syHpaas",syHpaas);
        map.put("syLkaas",syLkaas);
        map.put("syOwaas",syOwaas);
        map.put("syAcaas",syAcaas);

        return "html/river/fxSelect";
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
    @RequestMapping("/fx/baobiao")
    public String fxBaobiao(ModelMap map) {

        FxTableData fxTableData = riverService.getFxTableData();
        map.put("data",fxTableData);



        return "html/river/fxbaobiao";
    }


    @RequestMapping("/bgDetailItem")
    public String bgDetailItem(@RequestParam(value = "id", required = false) int id, ModelMap map) {

        ChangeWaterarea changeWaterarea = riverService.getChangeWaterById(id);

        map.put("data",changeWaterarea);

        return "html/river/bgDetailItem";
    }
    @RequestMapping("/bgSearch")
    public String bgSearch(@RequestParam(value = "value", required = false) String value, ModelMap map) {

        if(value !=null){
            List<ChangeWaterarea> changeWaterareas = riverService.getCwLikeNameOrBm(value);
            map.put("list",changeWaterareas);
        }else{
            map.put("value",null);
            map.put("list",null);
        }

        return "html/river/bgSearch";
    }

}
