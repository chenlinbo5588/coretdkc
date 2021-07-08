package com.clb.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
public class InspectionRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Integer glxmId;
    private String xcryName;
    private String xcdate;
    private String xcjl;
    private Integer xchdUid;
    private Integer addUid;
    private String addUsername;
    private Integer reviseUid;
    private String reviseName;


}
