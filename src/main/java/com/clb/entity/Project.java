package com.clb.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.sql.Date;
import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String pname;
    private String ptype;
    private Float mj;
    private String contacter;
    private String pfwh;
    private int xcdate;
    private String jsdw;
    private String jcdw;
    private String jldw;
    private String ztwfzl;
    private int ysdate;
    private Integer addUid;
    private String addUsername;
    private Integer editUid;
    private String editUsername;
    private String fzrname;
    private String fzrlxfs;
    private String remark;
    private String xypj;
    private String xmydhxName;


    @Transient
    private  String jsd;
    @Transient
    private  String zjxcsj;
    @Transient
    private  String yssj;
    @Transient
    private  String type;
    @Transient
    private  Date ysdateS;
    @Transient

    private  Date xcdateS;


}
