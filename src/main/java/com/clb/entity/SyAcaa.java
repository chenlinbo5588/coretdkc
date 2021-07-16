package com.clb.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
public class SyAcaa {

    private Integer objectid;
    private String name;
    private String code;
    private String city;
    private String county;
    private String sname;
    private String ename;
    private String length;
    private String width;
    private String area;
    private String vol;
    private String landform;
    private String town;
    private String ia;
    private String type;
    private String remark;
    private String sshd;
    private String zya;
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
