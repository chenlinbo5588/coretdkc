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
    private float length;
    private float width;
    private String sname;
    private String ename;
    private String garde;
    private String mntrb;
    private String trntype;
    private String town;
    private String bas;
    private String landform;
    private String function;
    private float area;
    private float vol;
    private String mu;
    private String imp;
    private Float sdl;
    private Float edl;
    private String rchief;
    private String remark;
    private String hdfl;
    private String sshd;
    private String rcode;
    private String hlm;
    private String ssqx;
    private Integer objectid;
    private  String contdiff;
    private  String spcl;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


}
