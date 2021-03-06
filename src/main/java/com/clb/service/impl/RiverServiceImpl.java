package com.clb.service.impl;

import com.clb.constant.DateConstant;
import com.clb.dto.*;
import com.clb.entity.*;
import com.clb.repository.jpa.*;
import com.clb.service.RiverService;
import io.github.perplexhub.rsql.RSQLJPASupport;
import lombok.SneakyThrows;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
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
    ChangeWaterareaRepository changeWaterareaRepository;
    @Autowired
    RestTemplate restTemplate;

    public List<SyRvaa> getByLikeName(String name) {

        String filer = "name=like=" + name;

        return syRvaaRepository.findAll(toSpecification(filer));
    }

    public SyRvaa getByName(String name) {
        return syRvaaRepository.findByName(name);
    }

    public List<SyRvaa> getByLikeNameAndTypes(String name, String[] types) {
        String filer = "name=like=" + name + ";type=in=(";

        for (int i = 0; i < types.length; i++) {
            filer = filer + types[i];
            if (i == (types.length - 1)) {
                filer = filer + ")";
            } else {
                filer = filer + ",";
            }
        }
        return syRvaaRepository.findAll(toSpecification(filer));
    }

    public List<SyRvaa> getByLikeNameOrBm(String value) {
        String filer = "name=like=" + value + ",bm=like=" + value;

        return syRvaaRepository.findAll(toSpecification(filer));
    }


    @SneakyThrows
    public List<WaterTj> getTypeList() {

        List<WaterTj> waterTjs = new ArrayList<WaterTj>();
        WaterTj waterTj = new WaterTj();

        Map<String, String> rvaa = syRvaaRepository.aggregate();
        BeanUtils.populate(waterTj, rvaa);
        waterTj.setType("??????");
        waterTjs.add(0, waterTj);

        Map<String, String> rsaa = syRsaaRepository.aggregate();
        waterTj = new WaterTj();
        BeanUtils.populate(waterTj, rsaa);
        waterTj.setType("??????");
        waterTjs.add(1, waterTj);

        Map<String, String> hpaa = syHpaaRepository.aggregate();
        waterTj = new WaterTj();
        BeanUtils.populate(waterTj, hpaa);
        waterTj.setType("??????");
        waterTjs.add(2, waterTj);

        Map<String, String> lkaa = syLkaaRepository.aggregate();
        waterTj = new WaterTj();
        BeanUtils.populate(waterTj, hpaa);
        waterTj.setType("??????");
        waterTjs.add(3, waterTj);


        Map<String, String> owaa = syOwaaRepository.aggregate();
        waterTj = new WaterTj();
        BeanUtils.populate(waterTj, owaa);
        waterTj.setType("????????????");
        waterTjs.add(4, waterTj);

        Map<String, String> acaa = syAcaaRepositoryaa.aggregate();
        waterTj = new WaterTj();
        BeanUtils.populate(waterTj, acaa);
        waterTj.setType("????????????");
        waterTjs.add(5, waterTj);

        return waterTjs;
    }

    public List<Tubiao> getTubiaoList(String type) throws InvocationTargetException, IllegalAccessException {

        List<Tubiao> tubiaos = new ArrayList<Tubiao>();

        if (type.equals("??????")) {
            getTubiaoHd(tubiaos);
            dealtubiaos(tubiaos);
        } else if (type.equals("??????")) {
            getTubiaoSk(tubiaos);
            dealtubiaos(tubiaos);
        } else if (type.equals("??????")) {
            getTubiaoSt(tubiaos);
            dealtubiaos(tubiaos);
        } else if (type.equals("????????????")) {
            getTubiaoAc(tubiaos);
            dealtubiaos(tubiaos);
        }

        return tubiaos;

    }

    public void getTubiaoSt(List<Tubiao> tubiaos) throws InvocationTargetException, IllegalAccessException {

        Tubiao tubiao = new Tubiao();
        TubiaoData tubiaoData = new TubiaoData();

        tubiao = new Tubiao();
        tubiao.setData(new ArrayList<TubiaoData>());
        tubiao.setName("??????????????????");
        List<Map<String, String>> area = syHpaaRepository.sumAreathgroupbyType();
        for (int i = 0; i < area.size(); i++) {
            tubiaoData = new TubiaoData();
            BeanUtils.populate(tubiaoData, area.get(i));
            tubiaoData.setNumberDw("???");
            tubiaoData.setType("??????");
            tubiaoData.setDw("km??");
            tubiao.getData().add(tubiaoData);
        }
        tubiaos.add(tubiao);


        tubiao = new Tubiao();
        tubiao.setData(new ArrayList<TubiaoData>());
        tubiao.setName("??????????????????");
        List<Map<String, String>> vol = syHpaaRepository.sumVolthgroupbyType();
        for (int i = 0; i < area.size(); i++) {
            tubiaoData = new TubiaoData();
            BeanUtils.populate(tubiaoData, vol.get(i));
            tubiaoData.setNumberDw("???");
            tubiaoData.setType("??????");
            tubiaoData.setDw("???km??");
            tubiao.getData().add(tubiaoData);
        }
        tubiaos.add(tubiao);
    }

    public void getTubiaoSk(List<Tubiao> tubiaos) throws InvocationTargetException, IllegalAccessException {

        Tubiao tubiao = new Tubiao();
        TubiaoData tubiaoData = new TubiaoData();

        tubiao = new Tubiao();
        tubiao.setData(new ArrayList<TubiaoData>());
        tubiao.setName("??????????????????");
        List<Map<String, String>> area = syRsaaRepository.sumAreathgroupbyType();
        for (int i = 0; i < area.size(); i++) {
            tubiaoData = new TubiaoData();
            BeanUtils.populate(tubiaoData, area.get(i));
            tubiaoData.setNumberDw("???");
            tubiaoData.setType("??????");
            tubiaoData.setDw("km??");
            tubiao.getData().add(tubiaoData);
        }
        tubiaos.add(tubiao);


        tubiao = new Tubiao();
        tubiao.setData(new ArrayList<TubiaoData>());
        tubiao.setName("??????????????????");
        List<Map<String, String>> vol = syRsaaRepository.sumVolthgroupbyType();
        for (int i = 0; i < area.size(); i++) {
            tubiaoData = new TubiaoData();
            BeanUtils.populate(tubiaoData, vol.get(i));
            tubiaoData.setNumberDw("???");
            tubiaoData.setType("??????");
            tubiaoData.setDw("???km??");
            tubiao.getData().add(tubiaoData);
        }
        tubiaos.add(tubiao);
    }

    public void getTubiaoHd(List<Tubiao> tubiaos) throws InvocationTargetException, IllegalAccessException {

        Tubiao tubiao = new Tubiao();
        TubiaoData tubiaoData = new TubiaoData();


        tubiao = new Tubiao();
        tubiao.setData(new ArrayList<TubiaoData>());
        tubiao.setName("??????????????????");
        List<Map<String, String>> length = syRvaaRepository.sumLengthgroupbyHdfl();
        for (int i = 0; i < length.size(); i++) {
            tubiaoData = new TubiaoData();
            BeanUtils.populate(tubiaoData, length.get(i));
            tubiaoData.setNumberDw("???");
            tubiaoData.setType("??????");
            tubiaoData.setDw("km");
            tubiao.getData().add(tubiaoData);
        }
        tubiaos.add(tubiao);

        tubiao = new Tubiao();
        tubiao.setData(new ArrayList<TubiaoData>());
        tubiao.setName("??????????????????");
        List<Map<String, String>> area = syRvaaRepository.sumAreagroupbyHdfl();
        for (int i = 0; i < area.size(); i++) {
            tubiaoData = new TubiaoData();
            BeanUtils.populate(tubiaoData, area.get(i));
            tubiaoData.setNumberDw("???");
            tubiaoData.setType("??????");
            tubiaoData.setDw("km??");
            tubiao.getData().add(tubiaoData);
        }
        tubiaos.add(tubiao);


        tubiao = new Tubiao();
        tubiao.setData(new ArrayList<TubiaoData>());
        tubiao.setName("??????????????????");
        List<Map<String, String>> vol = syRvaaRepository.sumVolgroupbyHdfl();
        for (int i = 0; i < area.size(); i++) {
            tubiaoData = new TubiaoData();
            BeanUtils.populate(tubiaoData, vol.get(i));
            tubiaoData.setNumberDw("???");
            tubiaoData.setType("??????");
            tubiaoData.setDw("???km??");
            tubiao.getData().add(tubiaoData);
        }
        tubiaos.add(tubiao);
    }

    public void getTubiaoAc(List<Tubiao> tubiaos) throws InvocationTargetException, IllegalAccessException {

        Tubiao tubiao = new Tubiao();
        TubiaoData tubiaoData = new TubiaoData();


        tubiao = new Tubiao();
        tubiao.setData(new ArrayList<TubiaoData>());
        tubiao.setName("????????????????????????");
        List<Map<String, String>> length = syAcaaRepositoryaa.sumLengthgroupbyType();
        for (int i = 0; i < length.size(); i++) {
            tubiaoData = new TubiaoData();
            BeanUtils.populate(tubiaoData, length.get(i));
            tubiaoData.setNumberDw("???");
            tubiaoData.setType("??????");
            tubiaoData.setDw("km");
            tubiao.getData().add(tubiaoData);
        }
        tubiaos.add(tubiao);

        tubiao = new Tubiao();
        tubiao.setData(new ArrayList<TubiaoData>());
        tubiao.setName("????????????????????????");
        List<Map<String, String>> area = syAcaaRepositoryaa.sumAreagroupbyType();
        for (int i = 0; i < area.size(); i++) {
            tubiaoData = new TubiaoData();
            BeanUtils.populate(tubiaoData, area.get(i));
            tubiaoData.setNumberDw("???");
            tubiaoData.setType("??????");
            tubiaoData.setDw("km??");
            tubiao.getData().add(tubiaoData);
        }
        tubiaos.add(tubiao);


        tubiao = new Tubiao();
        tubiao.setData(new ArrayList<TubiaoData>());
        tubiao.setName("????????????????????????");
        List<Map<String, String>> vol = syAcaaRepositoryaa.sumVolgroupbyType();
        for (int i = 0; i < area.size(); i++) {
            tubiaoData = new TubiaoData();
            BeanUtils.populate(tubiaoData, vol.get(i));
            tubiaoData.setNumberDw("???");
            tubiaoData.setType("??????");
            tubiaoData.setDw("???km??");
            tubiao.getData().add(tubiaoData);
        }
        tubiaos.add(tubiao);
    }

    public void dealtubiaos(List<Tubiao> tubiaos) {

        for (int i = 0; i < tubiaos.size(); i++) {
            Float sum = getSumValue(tubiaos.get(i).getData());
            for (int n = 0; n < tubiaos.get(i).getData().size(); n++) {
                if (sum > 0) {
                    tubiaos.get(i).getData().get(n).setZb((float) Math.round((tubiaos.get(i).getData().get(n).getValue() / sum * 100) * 100) / 100);
                } else {
                    tubiaos.get(i).getData().get(n).setZb(0);
                }
                tubiaos.get(i).getData().get(n).setValue((float) Math.round(tubiaos.get(i).getData().get(n).getValue() * 100) / 100);
            }
        }
    }

    public float getSumValue(List<TubiaoData> tubiaoData) {
        float sum = 0;
        for (int i = 0; i < tubiaoData.size(); i++) {
            sum = sum + tubiaoData.get(i).getValue();
        }
        return sum;
    }

    public SyRvaa getRvaaBycode(String code) {
        String filer = "code==" + code;
        if (syRvaaRepository.findAll(toSpecification(filer)).size() > 0) {
            return syRvaaRepository.findAll(toSpecification(filer)).get(0);
        } else {
            return null;
        }
    }

    public SyOwaa getOwaaBycode(String code) {
        String filer = "code==" + code;

        if (syOwaaRepository.findAll(toSpecification(filer)).size() > 0) {
            return syOwaaRepository.findAll(toSpecification(filer)).get(0);
        } else {
            return null;
        }
    }

    public SyRsaa getRsaaBycode(String code) {
        String filer = "code==" + code;

        if (syRsaaRepository.findAll(toSpecification(filer)).size() > 0) {
            return syRsaaRepository.findAll(toSpecification(filer)).get(0);
        } else {
            return null;
        }
    }

    public SyLkaa getLkaaBycode(String code) {
        String filer = "code==" + code;
        if (syLkaaRepository.findAll(toSpecification(filer)).size() > 0) {
            return syLkaaRepository.findAll(toSpecification(filer)).get(0);
        } else {
            return null;
        }
    }

    public SyHpaa getHpaaBycode(String code) {
        String filer = "code==" + code;
        if (syHpaaRepository.findAll(toSpecification(filer)).size() > 0) {
            return syHpaaRepository.findAll(toSpecification(filer)).get(0);
        } else {
            return null;
        }
    }

    public SyAcaa getAcaaBycode(String code) {
        String filer = "code==" + code;
        if (syAcaaRepositoryaa.findAll(toSpecification(filer)).size() > 0) {
            return syAcaaRepositoryaa.findAll(toSpecification(filer)).get(0);
        } else {
            return null;
        }
    }

    public SyRvaa getRvById(int id) {
        return syRvaaRepository.findAllById(Collections.singleton(id)).get(0);
    }

    public SyRsaa getRsById(int id) {
        return syRsaaRepository.findAllById(Collections.singleton(id)).get(0);
    }

    public SyLkaa getLkById(int id) {
        return syLkaaRepository.findAllById(Collections.singleton(id)).get(0);
    }

    public SyHpaa getHpById(int id) {
        return syHpaaRepository.findAllById(Collections.singleton(id)).get(0);
    }

    public SyAcaa getAcById(int id) {
        return syAcaaRepositoryaa.findAllById(Collections.singleton(id)).get(0);
    }

    public SyOwaa getOwById(int id) {
        return syOwaaRepository.findAllById(Collections.singleton(id)).get(0);
    }


    public List<SyRvaa> getRvLikeNameOrBm(String value) {
        String filer = "name=like=" + value + ",code=like=" + value;
        return syRvaaRepository.findAll(toSpecification(filer));
    }

    public List<SyRsaa> getRsLikeNameOrBm(String value) {
        String filer = "name=like=" + value + ",code=like=" + value;
        return syRsaaRepository.findAll(toSpecification(filer));
    }

    public List<SyLkaa> getLkLikeNameOrBm(String value) {
        String filer = "name=like=" + value + ",code=like=" + value;
        return syLkaaRepository.findAll(toSpecification(filer));
    }

    public List<SyHpaa> getHpLikeNameOrBm(String value) {
        String filer = "name=like=" + value + ",code=like=" + value;
        return syHpaaRepository.findAll(toSpecification(filer));
    }

    public List<SyAcaa> getAcLikeNameOrBm(String value) {
        String filer = "name=like=" + value + ",code=like=" + value;
        return syAcaaRepositoryaa.findAll(toSpecification(filer));
    }

    public List<SyOwaa> getOwLikeNameOrBm(String value) {
        String filer = "name=like=" + value + ",code=like=" + value;
        return syOwaaRepository.findAll(toSpecification(filer));
    }

    public List<ChangeWaterarea> getChangeWaterareaOrderBgdate() {
        Specification<ChangeWaterarea> specification = RSQLJPASupport.<ChangeWaterarea>toSort("bgdate,desc");
        return changeWaterareaRepository.findAll(specification);
    }

    public List<ChangeWaterarea> getChangeWatersById(int id) {
        String filer = "id==" + id;
        ChangeWaterarea changeWaterarea = changeWaterareaRepository.findAll(toSpecification(filer)).get(0);
        List<ChangeWaterarea> changeWaterareas = new ArrayList<ChangeWaterarea>();
        if (changeWaterarea.getProjectId() == null) {
            changeWaterareas.add(changeWaterarea);
        } else {
            filer = "projectId==" + changeWaterarea.getProjectId();
            Specification<ChangeWaterarea> specification = RSQLJPASupport.<ChangeWaterarea>toSort("bgdate,asc").and(RSQLJPASupport.toSpecification(filer));
            changeWaterareas = changeWaterareaRepository.findAll(specification);
        }
        return changeWaterareas;
    }

    public ChangeWaterarea getChangeWaterById(int id) {
        String filer = "id==" + id;
        ChangeWaterarea changeWaterarea = changeWaterareaRepository.findAll(toSpecification(filer)).get(0);
        return changeWaterarea;
    }

    public List<ChangeWaterarea> getCwLikeNameOrBm(String value) {

        String filer = "bgName=like=" + value + ",projectName=like=" + value;
        List<ChangeWaterarea> changeWaterareas = changeWaterareaRepository.findAll(toSpecification(filer));
        return changeWaterareas;
    }

    public FxTableData getFxTableData() {
        String[] syNames = DateConstant.SY_TYPENAME;
        FxTableData fxTableData = new FxTableData();
        fxTableData.setTypeName(DateConstant.FX_TYPENAME);
        fxTableData.setXName(syNames);
        FxTableList fxTableListAdd = new FxTableList();
        FxTableList fxTableListReduce = new FxTableList();
        FxTableList fxTableListSum = new FxTableList();
        fxTableListAdd.setName(DateConstant.FX_TYPENAME[0]);
        fxTableListReduce.setName(DateConstant.FX_TYPENAME[1]);
        fxTableListSum.setName(DateConstant.FX_TYPENAME[2]);

        fxTableListAdd.setType("bar");
        fxTableListReduce.setType("bar");
        fxTableListSum.setType("bar");
        List<ChangeWaterarea> changeWaterareas;
        float areaAdd = 0;
        float areaReduce = 0;
        float areaSum = 0;
        for (int i = 0; i < syNames.length; i++) {
            areaAdd = 0;
            areaReduce = 0;
            areaSum = 0;
            //??????????????????????????????????????????????????? 1????????? 2?????????
            for (int n = 1; n < 3; n++) {
                String filer = "syType==" + syNames[i] + ";bgAreatype==" + n;
                changeWaterareas = changeWaterareaRepository.findAll(toSpecification(filer));
                for (int o = 0; o < changeWaterareas.size(); o++) {
                    if (n == 1) {
                        areaAdd = areaAdd + changeWaterareas.get(o).getBgArea();
                    } else {
                        areaReduce = areaReduce + changeWaterareas.get(o).getBgArea();
                    }
                }
            }
            areaSum = areaAdd - areaReduce;
            fxTableListAdd.getData().add(String.valueOf(areaAdd));
            fxTableListReduce.getData().add(String.valueOf(-1.0*areaReduce));
            fxTableListSum.getData().add(String.valueOf(areaSum));
        };
        fxTableData.getFxTableList().add(fxTableListAdd);
        fxTableData.getFxTableList().add(fxTableListReduce);
        fxTableData.getFxTableList().add(fxTableListSum);

        return fxTableData;
    }

}
