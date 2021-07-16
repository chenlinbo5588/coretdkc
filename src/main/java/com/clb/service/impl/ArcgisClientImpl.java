package com.clb.service.impl;

import com.clb.componet.ArcgisConfig;
import com.clb.dto.ArcgisToken;
import com.clb.service.ArcgisClient;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


@Service
public class ArcgisClientImpl implements ArcgisClient {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    ArcgisConfig arcgisConfig;

    public String getArcgisServerUrl(){
        return "http://" + arcgisConfig.getArcgisMapHost();
    }


    @Override
    public ArcgisToken fetchToken(String username, String password) {

        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("f", "json");
        params.add("username", username);
        params.add("password", password);
        params.add("client", "requestip");
        params.add("expiration", "1440");

        ResponseEntity<String> result = restTemplate.postForEntity(getArcgisServerUrl() + "/arcgis/tokens/",params,String.class);
        Gson gson = new Gson();

        ArcgisToken token = null;

        try {
            token = gson.fromJson(result.getBody(),ArcgisToken.class);
        } catch (Exception e){

        } finally {

        }

        return token;
    }
}
