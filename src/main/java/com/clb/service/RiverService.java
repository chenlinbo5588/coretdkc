package com.clb.service;

import com.clb.entity.River;

import java.util.List;


public interface RiverService {

    public List<River> getByLikeName(String name);

    public List<River> getByLikeNameOrBm(String value);

    public River getByName(String name);

    public List<River> getByLikeNameAndTypes(String name,String[] types);



}
