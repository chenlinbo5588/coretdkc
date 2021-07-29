package com.clb.controller;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Data
public class MapControllerAdvice {

    @Value("${arcgis.host}")
    private String arcgisMapHost;

    @Value("${arcgis.mapServerName}")
    private String appMapServerName;

    @Value("${server.port}")
    private String port;

    @Value("${project.host}")
    private String xmHost;

    @Value("${arcgis.outputDwgUrl}")
    private String outputDwgUrl;

    @Value("${arcgis.outputDwgDownloadUrl}")
    private String outputDwgDownloadUrl;

    @Value("${arcgis.outputallDwgUrl}")
    private String outputallDwgUrl;

    @Value("${arcgis.outputallDwgDownloadUrl}")
    private String outputallDwgDownloadUrl;

    @ModelAttribute(value = "mapConfig")
    public Map<String,Object> mydata()
    {
        Map<String,Object> map = new HashMap<>();
        map.put("host",arcgisMapHost);
        map.put("mapServerName",appMapServerName);
        map.put("port",port);
        map.put("xmhost",xmHost);
        map.put("outputDwgUrl",outputDwgUrl);
        map.put("outputDwgDownloadUrl",outputDwgDownloadUrl);
        map.put("outputallDwgUrl",outputallDwgUrl);
        map.put("outputallDwgDownloadUrl",outputallDwgDownloadUrl);



        return map;
    }

//    @ModelAttribute
//    public String maphost(Model model)
//    {
//        model.addAttribute("mapHost",arcgisMapHost);
//        return arcgisMapHost;
//    }


}
