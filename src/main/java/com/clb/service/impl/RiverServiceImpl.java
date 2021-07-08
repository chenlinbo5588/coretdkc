package com.clb.service.impl;

import com.clb.entity.*;
import com.clb.repository.jpa.*;
import com.clb.service.RiverService;
import lombok.SneakyThrows;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static io.github.perplexhub.rsql.RSQLJPASupport.toSpecification;


@Service
public class RiverServiceImpl implements RiverService {

    @Autowired
    RiverRepository riverRepository;

    @Autowired
    SyAcaaRepository syAcaaRepositoryaa;
    @Autowired
    SyHpaaRepository syHpaaRepository;
    @Autowired
    SyLkaaRepository syLkaaRepository;
    @Autowired
    SyRsaaRepository syRsaaRepository;
    @Autowired
    SyRvaaRepository syRvaaRepository;
    @Autowired
    SyOwaaRepository syOwaaRepository;
    @Autowired
    RestTemplate restTemplate;

    public List<SyRvaa> getByLikeName(String name){

        String filer = "name=like="+name;

        return syRvaaRepository.findAll(toSpecification(filer));
    }

    public SyRvaa getByName(String name){
        return syRvaaRepository.findByName(name);
    }

    public List<SyRvaa> getByLikeNameAndTypes(String name,String[] types){
        String filer = "name=like="+name +";type=in=(" ;

        for(int i=0;i<types.length;i++){
            filer = filer + types[i] ;
            if(i ==(types.length-1)){
                filer = filer + ")" ;
            }else{
                filer = filer + "," ;
            }
        }
        return syRvaaRepository.findAll(toSpecification(filer));
    }

    public List<SyRvaa> getByLikeNameOrBm(String value){
        String filer = "name=like="+value +",bm=like="+ value ;

        return syRvaaRepository.findAll(toSpecification(filer));
    }
    public SyRvaa getById(int id){
        return syRvaaRepository.findAllById(Collections.singleton(id)).get(0);

    }

    @SneakyThrows
    public List<WaterTj> getTypeList(){

        List<WaterTj> waterTjs  =  new ArrayList<WaterTj>();
        WaterTj waterTj = new WaterTj();

        Map<String,String> rvaa = syRvaaRepository.aggregate();
        BeanUtils.populate(waterTj,rvaa);
        waterTj.setType("河道");
        waterTjs.add(0,waterTj);

        Map<String,String> rsaa = syRsaaRepository.aggregate();
        waterTj = new WaterTj();
        BeanUtils.populate(waterTj,rsaa);
        waterTj.setType("水库");
        waterTjs.add(1,waterTj);

        Map<String,String> hpaa = syHpaaRepository.aggregate();
        waterTj = new WaterTj();
        BeanUtils.populate(waterTj,hpaa);
        waterTj.setType("山塘");
        waterTjs.add(2,waterTj);

        Map<String,String> lkaa = syLkaaRepository.aggregate();
        waterTj = new WaterTj();
        BeanUtils.populate(waterTj,hpaa);
        waterTj.setType("湖泊");
        waterTjs.add(3,waterTj);


        Map<String,String> owaa = syOwaaRepository.aggregate();
        waterTj = new WaterTj();
        BeanUtils.populate(waterTj,owaa);
        waterTj.setType("其他水域");
        waterTjs.add(4,waterTj);

        Map<String,String> acaa = syAcaaRepositoryaa.aggregate();
        waterTj = new WaterTj();
        BeanUtils.populate(waterTj,acaa);
        waterTj.setType("人工水道");
        waterTjs.add(5,waterTj);

        return waterTjs;
    }

    public List<Tubiao> getTubiaoList(String type) throws InvocationTargetException, IllegalAccessException {

        List<Tubiao> tubiaos =new ArrayList<Tubiao>();

        if(type.equals("河道")){
            getTubiaoHd(tubiaos);
            dealtubiaos(tubiaos);
        }else if(type.equals("水库")){
            getTubiaoSk(tubiaos);
            dealtubiaos(tubiaos);
        }else if(type.equals("山塘")){
            getTubiaoSt(tubiaos);
            dealtubiaos(tubiaos);
        }else if(type.equals("人工水道")){
            getTubiaoAc(tubiaos);
            dealtubiaos(tubiaos);
        }

        return tubiaos;

    }
    public void getTubiaoSt( List<Tubiao> tubiaos) throws InvocationTargetException, IllegalAccessException {

        Tubiao tubiao = new Tubiao();
        TubiaoData tubiaoData = new TubiaoData();

        tubiao = new Tubiao();
        tubiao.setData(new ArrayList<TubiaoData>());
        tubiao.setName("山塘面积统计");
        List<Map<String, String>> area = syHpaaRepository.sumAreathgroupbyType();
        for (int i = 0; i < area.size(); i++) {
            tubiaoData = new TubiaoData();
            BeanUtils.populate(tubiaoData, area.get(i));
            tubiaoData.setNumberDw("座");
            tubiaoData.setType("面积");
            tubiaoData.setDw("km²");
            tubiao.getData().add(tubiaoData);
        }
        tubiaos.add(tubiao);


        tubiao = new Tubiao();
        tubiao.setData(new ArrayList<TubiaoData>());
        tubiao.setName("山塘容积统计");
        List<Map<String, String>> vol =syHpaaRepository.sumVolthgroupbyType();
        for (int i = 0; i < area.size(); i++) {
            tubiaoData = new TubiaoData();
            BeanUtils.populate(tubiaoData, vol.get(i));
            tubiaoData.setNumberDw("座");
            tubiaoData.setType("容积");
            tubiaoData.setDw("万km³");
            tubiao.getData().add(tubiaoData);
        }
        tubiaos.add(tubiao);
    }
    public void getTubiaoSk( List<Tubiao> tubiaos) throws InvocationTargetException, IllegalAccessException {

        Tubiao tubiao = new Tubiao();
        TubiaoData tubiaoData = new TubiaoData();

        tubiao = new Tubiao();
        tubiao.setData(new ArrayList<TubiaoData>());
        tubiao.setName("水库面积统计");
        List<Map<String, String>> area = syRsaaRepository.sumAreathgroupbyType();
        for (int i = 0; i < area.size(); i++) {
            tubiaoData = new TubiaoData();
            BeanUtils.populate(tubiaoData, area.get(i));
            tubiaoData.setNumberDw("座");
            tubiaoData.setType("面积");
            tubiaoData.setDw("km²");
            tubiao.getData().add(tubiaoData);
        }
        tubiaos.add(tubiao);


        tubiao = new Tubiao();
        tubiao.setData(new ArrayList<TubiaoData>());
        tubiao.setName("水库容积统计");
        List<Map<String, String>> vol =syRsaaRepository.sumVolthgroupbyType();
        for (int i = 0; i < area.size(); i++) {
            tubiaoData = new TubiaoData();
            BeanUtils.populate(tubiaoData, vol.get(i));
            tubiaoData.setNumberDw("座");
            tubiaoData.setType("容积");
            tubiaoData.setDw("万km³");
            tubiao.getData().add(tubiaoData);
        }
        tubiaos.add(tubiao);
    }
    public void getTubiaoHd( List<Tubiao> tubiaos) throws InvocationTargetException, IllegalAccessException {

        Tubiao tubiao = new Tubiao();
        TubiaoData tubiaoData = new TubiaoData();


        tubiao = new Tubiao();
        tubiao.setData(new ArrayList<TubiaoData>());
        tubiao.setName("河道长度统计");
        List<Map<String, String>> length = syRvaaRepository.sumLengthgroupbyHdfl();
        for (int i = 0; i < length.size(); i++) {
            tubiaoData = new TubiaoData();
            BeanUtils.populate(tubiaoData, length.get(i));
            tubiaoData.setNumberDw("条");
            tubiaoData.setType("长度");
            tubiaoData.setDw("km");
            tubiao.getData().add(tubiaoData);
        }
        tubiaos.add(tubiao);

        tubiao = new Tubiao();
        tubiao.setData(new ArrayList<TubiaoData>());
        tubiao.setName("河道面积统计");
        List<Map<String, String>> area = syRvaaRepository.sumAreagroupbyHdfl();
        for (int i = 0; i < area.size(); i++) {
            tubiaoData = new TubiaoData();
            BeanUtils.populate(tubiaoData, area.get(i));
            tubiaoData.setNumberDw("条");
            tubiaoData.setType("面积");
            tubiaoData.setDw("km²");
            tubiao.getData().add(tubiaoData);
        }
        tubiaos.add(tubiao);


        tubiao = new Tubiao();
        tubiao.setData(new ArrayList<TubiaoData>());
        tubiao.setName("河道容积统计");
        List<Map<String, String>> vol = syRvaaRepository.sumVolgroupbyHdfl();
        for (int i = 0; i < area.size(); i++) {
            tubiaoData = new TubiaoData();
            BeanUtils.populate(tubiaoData, vol.get(i));
            tubiaoData.setNumberDw("条");
            tubiaoData.setType("容积");
            tubiaoData.setDw("万km³");
            tubiao.getData().add(tubiaoData);
        }
        tubiaos.add(tubiao);
    }
    public void getTubiaoAc( List<Tubiao> tubiaos) throws InvocationTargetException, IllegalAccessException {

        Tubiao tubiao = new Tubiao();
        TubiaoData tubiaoData = new TubiaoData();


        tubiao = new Tubiao();
        tubiao.setData(new ArrayList<TubiaoData>());
        tubiao.setName("人工水道长度统计");
        List<Map<String, String>> length = syAcaaRepositoryaa.sumLengthgroupbyType();
        for (int i = 0; i < length.size(); i++) {
            tubiaoData = new TubiaoData();
            BeanUtils.populate(tubiaoData, length.get(i));
            tubiaoData.setNumberDw("条");
            tubiaoData.setType("长度");
            tubiaoData.setDw("km");
            tubiao.getData().add(tubiaoData);
        }
        tubiaos.add(tubiao);

        tubiao = new Tubiao();
        tubiao.setData(new ArrayList<TubiaoData>());
        tubiao.setName("人工水道面积统计");
        List<Map<String, String>> area = syAcaaRepositoryaa.sumAreagroupbyType();
        for (int i = 0; i < area.size(); i++) {
            tubiaoData = new TubiaoData();
            BeanUtils.populate(tubiaoData, area.get(i));
            tubiaoData.setNumberDw("条");
            tubiaoData.setType("面积");
            tubiaoData.setDw("km²");
            tubiao.getData().add(tubiaoData);
        }
        tubiaos.add(tubiao);


        tubiao = new Tubiao();
        tubiao.setData(new ArrayList<TubiaoData>());
        tubiao.setName("人工水道容积统计");
        List<Map<String, String>> vol = syAcaaRepositoryaa.sumVolgroupbyType();
        for (int i = 0; i < area.size(); i++) {
            tubiaoData = new TubiaoData();
            BeanUtils.populate(tubiaoData, vol.get(i));
            tubiaoData.setNumberDw("条");
            tubiaoData.setType("容积");
            tubiaoData.setDw("万km²");
            tubiao.getData().add(tubiaoData);
        }
        tubiaos.add(tubiao);
    }

    public void dealtubiaos( List<Tubiao> tubiaos){

        for(int i=0;i<tubiaos.size();i++){
            Float sum = getSumValue(tubiaos.get(i).getData());
            for(int n=0;n<tubiaos.get(i).getData().size();n++){
                if(sum > 0) {
                    tubiaos.get(i).getData().get(n).setZb ((float) Math.round((tubiaos.get(i).getData().get(n).getValue() / sum *100)*100)/100);
                }else {
                    tubiaos.get(i).getData().get(n).setZb(0);
                }
                tubiaos.get(i).getData().get(n).setValue((float) Math.round(tubiaos.get(i).getData().get(n).getValue() * 100) / 100);
            }
        }
    }
    public float getSumValue(List<TubiaoData> tubiaoData){
        float sum = 0 ;
        for(int i=0;i<tubiaoData.size();i++){
            sum = sum+tubiaoData.get(i).getValue();
        }
        return sum;
    }


}
