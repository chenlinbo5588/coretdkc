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

    @Value("${arcgisMapHost}")
    private String arcgisMapHost;

    @Value("${appMapServerName}")
    private String appMapServerName;

    @Value("${appMapServerPort}")
    private String appMapServerPort;

    @Value("${xmHost}")
    private String xmHost;


    @ModelAttribute(value = "mapConfig")
    public Map<String,Object> mydata()
    {
        Map<String,Object> map = new HashMap<>();
        map.put("host",arcgisMapHost);
        map.put("mapServerName",appMapServerName);
        map.put("port",appMapServerPort);
        map.put("xmhost",xmHost);

        return map;
    }

//    @ModelAttribute
//    public String maphost(Model model)
//    {
//        model.addAttribute("mapHost",arcgisMapHost);
//        return arcgisMapHost;
//    }


}
