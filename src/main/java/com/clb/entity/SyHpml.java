package com.clb.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
public class SyHpml {

    private Integer objectid;
    private String code;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


}
