package com.clb.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
public class SyLkaa {

    private String name;
    private String code;
    private String city;
    private String county;
    private float masl;
    private String mu;
    private String town;
    private String trntype;
    private String bas;
    private String landform;
    private float area;
    private float averdep;
    private float vol;
    private String function;
    private String imp;
    private String lchief;
    private String remark;
    private String ssqx;
    private Integer objectid;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


}
