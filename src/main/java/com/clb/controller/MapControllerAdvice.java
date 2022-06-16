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


    @Value("${arcgis.featureUrl.tdtJjUrl}")
    private String tdtJjUrl;

    @Value("${arcgis.featureUrl.tdtJjZjUrl}")
    private String tdtJjZjUrl;

    @Value("${arcgis.featureUrl.tdtYxUrl}")
    private String tdtYxUrl;
    @Value("${arcgis.featureUrl.tdtYxZjUrl}")
    private String tdtYxZjUrl;
    @Value("${arcgis.featureUrl.tdtDxUrl}")
    private String tdtDxUrl;
    @Value("${arcgis.featureUrl.tdtNbDzUrl}")
    private String tdtNbDzUrl;


    @Value("${arcgis.accessKey}")
    private String accessKey;
    @Value("${arcgis.secretKey}")
    private String secretKey;
    @Value("${arcgis.tdtToken}")
    private String tdtToken;

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

    @Value("${arcgis.referer}")
    private String referer;



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

        map.put("tdtJjUrl", tdtJjUrl);
        map.put("tdtJjZjUrl", tdtJjZjUrl);
        map.put("tdtYxUrl", tdtYxUrl);
        map.put("tdtYxZjUrl", tdtYxZjUrl);
        map.put("tdtDxUrl", tdtDxUrl);
        map.put("tdtNbDzUrl", tdtNbDzUrl);
        map.put("ak", accessKey);
        map.put("sk", secretKey);
        map.put("tdtToken", tdtToken);


        ArcgisToken token = arcgisClient.fetchToken(username,password,referer);
        map.put("token",token.getToken());
//        map.put("token","token");

        return map;
    }

}
