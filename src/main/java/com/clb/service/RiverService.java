package com.clb.service;

import com.clb.dto.FxTableData;
import com.clb.dto.FxTableList;
import com.clb.dto.Tubiao;
import com.clb.dto.WaterTj;
import com.clb.entity.*;

import java.lang.reflect.InvocationTargetException;
import java.util.List;


public interface RiverService {

    public List<SyRvaa> getByLikeName(String name);

    public List<SyRvaa> getByLikeNameOrBm(String value);

    public SyRvaa getByName(String name);

    public List<SyRvaa> getByLikeNameAndTypes(String name,String[] types);

    public List<WaterTj> getTypeList();

    public FxTableData getFxTableData();



    public List<Tubiao> getTubiaoList(String type) throws InvocationTargetException, IllegalAccessException;

    public SyRvaa getRvById(int id);
    public SyRsaa getRsById(int id);
    public SyLkaa getLkById(int id);
    public SyHpaa getHpById(int id);
    public SyAcaa getAcById(int id);
    public SyOwaa getOwById(int id);

    public  List<SyRvaa> getRvLikeNameOrBm(String value);
    public  List<SyRsaa> getRsLikeNameOrBm(String value);
    public  List<SyLkaa> getLkLikeNameOrBm(String value);
    public  List<SyHpaa> getHpLikeNameOrBm(String value);
    public  List<SyAcaa> getAcLikeNameOrBm(String value);
    public  List<SyOwaa> getOwLikeNameOrBm(String value);


    public SyRvaa getRvaaBycode(String code);
    public SyOwaa getOwaaBycode(String code);
    public SyRsaa getRsaaBycode(String code);
    public SyLkaa getLkaaBycode(String code);
    public SyHpaa getHpaaBycode(String code);
    public SyAcaa getAcaaBycode(String code);

    public List<ChangeWaterarea> getChangeWaterareaOrderBgdate();

    public List<ChangeWaterarea> getChangeWatersById(int id);

    public ChangeWaterarea getChangeWaterById(int id);

    public List<ChangeWaterarea> getCwLikeNameOrBm(String value);

}
