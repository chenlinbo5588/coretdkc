package com.clb.entity;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;


@Entity
@Data
@NoArgsConstructor
public class ChangeWaterarea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String projectName;
    private Integer addUid;
    private String addUsername;
    private Integer editUid;
    private String editUsername;
    private Long bgdate;
    private String bgBm;
    private String sgdw;
    private String sqdw;
    private String bgdw;
    private String changeType;
    private String changeRemark;
    private Float bgArea;
    private Short bgType;
    private String syType;
    private String bgAttribute;
    private Integer projectId;
    private Integer bgObjectid;
    private String bgqAttribute;
    private String bgName;

    public List<Map<String,String>> getBgLine(){
        Gson gson = new Gson();
        List<Map<String,String>> maps = gson.fromJson(getBgAttribute(), new TypeToken<List<Map<String,String>>>(){}.getType());
        return maps;
    }

    public String getBgDateTime(){
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        return ft.format(getBgdate()*1000);
    }





}
