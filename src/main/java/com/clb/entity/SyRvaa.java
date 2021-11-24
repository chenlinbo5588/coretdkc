package com.clb.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
public class SyRvaa {

    private String name;
    private String code;
    private String city;
    private String county;
    private Double length;
    private String width;
    private String sname;
    private String ename;
    private String garde;
    private String mntrb;
    private String trntype;
    private String town;
    private String bas;
    private String landform;
    private String function;
    private String area;
    private String vol;
    private String mu;
    private String imp;
    private String sdl;
    private String edl;
    private String rchief;
    private String remark;
    private String hdfl;
    private String sshd;
    private String rcode;
    private String hlm;
    private String ssqx;
    private Integer objectid;
    private String contdiff;
    private String spcl;
    private String identification;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Transient
    private  float intersectionArea;

    @Transient
    private  float intersectionLength;

    @Transient
    private  int layerId;

}
