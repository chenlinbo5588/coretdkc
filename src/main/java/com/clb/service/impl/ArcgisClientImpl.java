package com.clb.service.impl;

import com.clb.pojo.arcgis.ArcgisQueryResult;
import com.clb.service.ArcgisClient;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;


@Service
public class ArcgisClientImpl implements ArcgisClient {

    @Autowired
    RestTemplate restTemplate;

    public String baseProxyUrl  ="http://localhost:8080/river/proxy?";
    public String baseArcgisUrl = "http://localhost/arcgis/rest/services/";

    public ArcgisQueryResult queryByObjId(String id, Map<String,String[]> data){
        Gson gson = new Gson();
        String url = baseProxyUrl +data.get("mapurl")[0] +"/query?f=json&outFields=" +  data.get("outFields")[0] +"&spatialRel=esriSpatialRelIntersects&where=OBJECTID=" + id;

        String result = restTemplate.getForObject(url, String.class);
        ArcgisQueryResult arcgisQueryResult =  gson.fromJson(result, ArcgisQueryResult.class);

        return arcgisQueryResult;
    }


}
