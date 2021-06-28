package com.clb.service;


import com.clb.RiverWebApplication;
import com.clb.entity.River;

import com.clb.pojo.arcgis.ArcgisQueryResult;
import com.clb.repository.jpa.RiverRepository;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static io.github.perplexhub.rsql.RSQLJPASupport.toSpecification;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RiverWebApplication.class)
public class RiverServiceTests {


    @Autowired
    private RiverRepository riverRepository;


    @Autowired
    RestTemplate restTemplate;


    @Test
    public void testFetchRiverType(){
        String filer = "name=like=四"; //like %em%

        List<River> list = riverRepository.findAll(toSpecification(filer));

        assertEquals(list.size(),20);

    }


    @Test
    public void testClient(){
        final String uri = "http://localhost/arcgis/rest/services/yaosu/wangge/FeatureServer/0/query?f=json&outFields=*&spatialRel=esriSpatialRelIntersects&where=OBJECTID=18521";

        String result = restTemplate.getForObject(uri, String.class);

        Gson gson = new Gson();
        ArcgisQueryResult arcgisQueryResult =  gson.fromJson(result, ArcgisQueryResult.class);
        System.out.println(result);
    }


}
