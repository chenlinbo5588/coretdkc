package com.clb.controller;

import com.clb.pojo.arcgis.ArcgisQueryResult;
import com.clb.service.ArcgisClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
public class RiverRestController {

    @Resource
    ArcgisClient arcgisClient;

    @RequestMapping("/queryById/{id}")
    public ArcgisQueryResult query(@PathVariable(name = "id") String id, HttpServletRequest request){

        Map<String,String[]> data = request.getParameterMap();

        ArcgisQueryResult arcgisQueryResult = arcgisClient.queryByObjId(id,data);

        return arcgisQueryResult;
    }


}
