package com.clb.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
public class SyRvcl {
    private String code;
    private String name;
    private float length;
    private Integer objectid;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


}
