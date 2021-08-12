package com.clb.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
public class SyRsaa {
    private Integer objectid;
    private String name;
    private String code;
    private String city;
    private String county;
    private String type;
    private float rcarea;
    private float tcr;
    private float ucr;
    private float npl;
    private float iml;
    private float dfl;
    private float mfl;
    private String bas;
    private String landform;
    private String area;
    private String ce;
    private String function;
    private String imp;
    private String bldtm;
    private String mu;
    private String town;
    private String lchief;
    private String contdiff;
    private String spcl;
    private String remark;
    private String ssqx;
    private String identification;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Transient
    private  float intersectionArea;

    @Transient
    private  int layerId;


}
