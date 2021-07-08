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
    private float length;
    private float width;
    private float area;
    private float vol;
    private String landform;
    private String town;
    private String ia;
    private String type;
    private String remark;
    private String sshd;
    private String zya;
    private String ssqx;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


}
