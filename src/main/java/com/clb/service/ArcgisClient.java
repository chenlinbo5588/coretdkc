package com.clb.service;

import com.clb.pojo.arcgis.ArcgisQueryResult;

import java.util.Map;


public interface ArcgisClient {

        public ArcgisQueryResult queryByObjId(String id, Map<String,String[]> data);

}
