package com.clb.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;
@Entity
@Data
@NoArgsConstructor
public class SyHpaa {
    private Integer objectid;
    private String name;
    private String code;
    private String city;
    private String county;
    private String dmh;
    private String rcarea;
    private String bas;
    private String landform;
    private String town;
    private String rnvtm;
    private String tcr;
    private String type;
    private String ce;
    private Float dfl;
    private String npl;
    private String area;
    private String vol;
    private String function;
    private String remark;
    private String ssqx;
    private String contdiff;
    private String spcl;
    private String identification;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Transient
    private  float intersectionArea;

    @Transient
    private  int layerId;
}
