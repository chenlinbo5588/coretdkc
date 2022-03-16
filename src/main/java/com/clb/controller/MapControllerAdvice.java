package com.clb.controller;

import com.clb.dto.ArcgisToken;
import com.clb.service.ArcgisClient;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Data
public class MapControllerAdvice {

    @Autowired
    ArcgisClient arcgisClient;




    @Value("${server.port}")
    private String port;


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

    @Value("${arcgis.token.username}")
    private String username;

    @Value("${arcgis.token.password}")
    private String password;



    @ModelAttribute(value = "mapConfig")
    public Map<String, Object> mydata(HttpServletRequest request) throws UnknownHostException {
        Map<String, Object> map = new HashMap<>();

//        String referer = request.getServerName()+":"+port ;
        String xmhost = request.getServerName();

        map.put("port", port);
        map.put("xmhost", xmhost);
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

        ArcgisToken token = arcgisClient.fetchToken(username,password,"192.168.200.148:8080");
        map.put("token",token.getToken());
//        map.put("token","token");

        return map;
    }

}
