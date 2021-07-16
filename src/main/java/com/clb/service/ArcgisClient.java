package com.clb.service;

import com.clb.dto.ArcgisToken;
import com.clb.pojo.arcgis.ArcgisQueryResult;

import java.util.Map;


public interface ArcgisClient {
        public ArcgisToken fetchToken(String username, String password);

}
