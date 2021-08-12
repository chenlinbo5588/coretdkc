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

    @Value("${arcgis.featureToCadDownloadUrl}")
    private String featureToCadDownloadUrl;

    @Value("${arcgis.featureToCadurl}")
    private String featureToCadurl;

    @Value("${arcgis.featureUrl.temppolygonUrl}")
    private String temppolygonUrl;
    @Value("${arcgis.featureUrl.riverUrl}")
    private String riverUrl;
    @Value("${arcgis.featureUrl.otherUrl}")
    private String otherUrl;
    @Value("${arcgis.featureUrl.bgtxUrl}")
    private String bgtxUrl;

    @Value("${arcgis.featureUrl.hxUrl}")
    private String hxUrl;
    @Value("${arcgis.featureUrl.hxSearchUrl}")
    private String hxSearchUrl;
    @Value("${arcgis.featureUrl.riverQzjUrl}")
    private String riverQzjUrl;

    @Value("${arcgis.geometryServerUrl}")
    private String geometryServerUrl;

    @ModelAttribute(value = "mapConfig")
    public Map<String, Object> mydata() {
        Map<String, Object> map = new HashMap<>();
        map.put("host", arcgisMapHost);
        map.put("mapServerName", appMapServerName);
        map.put("port", port);
        map.put("xmhost", xmHost);
        map.put("outputDwgUrl", outputDwgUrl);
        map.put("outputDwgDownloadUrl", outputDwgDownloadUrl);
        map.put("outputallDwgUrl", outputallDwgUrl);
        map.put("featureToCadDownloadUrl", featureToCadDownloadUrl);
        map.put("featureToCadurl", featureToCadurl);
        map.put("temppolygonUrl", temppolygonUrl);
        map.put("riverUrl", riverUrl);
        map.put("otherUrl", otherUrl);
        map.put("bgtxUrl", bgtxUrl);
        map.put("hxUrl", hxUrl);
        map.put("hxSearchUrl", hxSearchUrl);
        map.put("riverQzjUrl", riverQzjUrl);
        map.put("geometryServerUrl", geometryServerUrl);


        return map;
    }

//    @ModelAttribute
//    public String maphost(Model model)
//    {
//        model.addAttribute("mapHost",arcgisMapHost);
//        return arcgisMapHost;
//    }


}
