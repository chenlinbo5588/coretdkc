package com.clb.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
public class SyOwaa {
    private String name;
    private String code;
    private String city;
    private String county;
    private String town;
    private String trntype;
    private String bas;
    private String landform;
    private String area;
    private String averdep;
    private String vol;
    private String type;
    private String remark;
    private String ssqx;
    private String identification;
    private String contdiff;
    private String spcl;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Transient
    private  float intersectionArea;

    @Transient
    private  int layerId;

}
