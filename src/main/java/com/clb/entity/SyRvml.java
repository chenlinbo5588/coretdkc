package com.clb.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
public class SyRvml {
    private String code;
    private Integer bjectid;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


}
