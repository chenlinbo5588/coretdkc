package com.clb.service;

import com.clb.entity.River;
import com.clb.entity.SyRvaa;
import com.clb.entity.Tubiao;
import com.clb.entity.WaterTj;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;


public interface RiverService {

    public List<SyRvaa> getByLikeName(String name);

    public List<SyRvaa> getByLikeNameOrBm(String value);

    public SyRvaa getByName(String name);

    public List<SyRvaa> getByLikeNameAndTypes(String name,String[] types);

    public List<WaterTj> getTypeList();

    public List<Tubiao> getTubiaoList(String type) throws InvocationTargetException, IllegalAccessException;

    public SyRvaa getById(int id);



}
