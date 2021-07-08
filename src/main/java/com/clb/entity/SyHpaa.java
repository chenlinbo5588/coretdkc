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
    private float dmh;
    private float rcarea;
    private String bas;
    private String landform;
    private String town;
    private String rnvtm;
    private float tcr;
    private String type;
    private float ce;
    private Float dfl;
    private float npl;
    private float area;
    private float vol;
    private String function;
    private String remark;
    private String ssqx;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


}
