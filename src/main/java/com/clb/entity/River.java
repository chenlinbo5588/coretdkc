package com.clb.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class River {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private int objectid;
    @Column
    private String name;
    @Column
    private float cd;
    @Column
    private float kd;
    @Column
    private String sname;
    @Column
    private String ename;
    @Column
    private float area;
    @Column
    private float vol;
    @Column
    private String type;
    @Column
    private String fl;
    @Column
    private String bm;


}
