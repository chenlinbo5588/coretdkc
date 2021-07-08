package com.clb.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
public class SyAccl {
    private String name;
    private String code;
    private float length;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


}
