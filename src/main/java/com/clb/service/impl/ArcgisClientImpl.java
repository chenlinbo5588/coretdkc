package com.clb.service.impl;

import com.clb.componet.ArcgisConfig;
import com.clb.dto.ArcgisToken;
import com.clb.entity.SyToken;
import com.clb.repository.jpa.SyTokenRepository;
import com.clb.service.ArcgisClient;
import com.clb.utils.AesEncryptUtil;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;

import static io.github.perplexhub.rsql.RSQLJPASupport.toSpecification;


@Service
public class ArcgisClientImpl implements ArcgisClient {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    ArcgisConfig arcgisConfig;

    @Autowired
    SyTokenRepository syTokenRepository;

//    public String getArcgisServerUrl() {
//        return "https://" + arcgisConfig.getProurl();
//    }

    @Override
    public ArcgisToken fetchToken(String username, String password, String referer) {

        SyToken syToken;
        List<SyToken> syTokens = syTokenRepository.getSytokenByinfo(username, password, referer);
        if (syTokens.size() > 0) {
            syToken = syTokens.get(0);
            if(syToken.getExpiration() == null){
                syToken.setExpiration(Long.valueOf(0));
            }
        } else {
           syToken = new SyToken();
           syToken.setExpiration(Long.valueOf(0));
        }

        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        ArcgisToken token = new ArcgisToken();
        Long nowTime = new Date().getTime();

        if (syToken.getExpiration() < nowTime) {

            String mima = "";
            try{
                mima = AesEncryptUtil.desEncrypt(password);
            } catch (Exception e) {
                e.printStackTrace();
            }


            params.add("f", "json");
            params.add("username", username);

            params.add("password", mima);
            params.add("client", "referer");
            params.add("referer", referer);
            params.add("expiration", "1440");

            ResponseEntity<String> result = restTemplate.postForEntity("https://gis2.cxzhsl.cn/arcgis/tokens/", params, String.class);
            Gson gson = new Gson();

            try {
                token = gson.fromJson(result.getBody(), ArcgisToken.class);
                syToken.setToken(token.getToken());
                syToken.setExpiration(token.getExpires());
                syToken.setName(username);
                syToken.setPassword(password);
                syToken.setReferer(referer);
                saveSytoken(syToken);
            } catch (Exception e) {

            } finally {

            }
        } else {
            token.setToken(syToken.getToken());
            token.setExpires(syToken.getExpiration());
        }

        return token;
    }

    public SyToken getSytoken(String username, String password, String referer) {

        String filer = "name==" + username + ";referer==" + referer + ";password==" + password ;

        List<SyToken> syTokenList = syTokenRepository.findAll(toSpecification(filer));
        if (syTokenList.size() > 0) {
            return syTokenList.get(0);
        } else {
            SyToken syToken = new SyToken();
            syToken.setExpiration(Long.valueOf(0));
            return syToken;
        }

    }

    public void saveSytoken(SyToken syToken) {
        syTokenRepository.save(syToken);
    }

}
