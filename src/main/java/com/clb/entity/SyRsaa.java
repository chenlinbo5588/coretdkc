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
    private String rcarea;
    private String tcr;
    private String ucr;
    private String npl;
    private String iml;
    private String dfl;
    private String mfl;
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
