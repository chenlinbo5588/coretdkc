package com.clb.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "auth_table")
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "auth_id")
    private Integer authorityId;
    @Column(name = "auth_name")
    private String authorityName;
    @Column(name = "group_data")
    private String groupData;
    @Column(name = "user_cnt")
    private Integer userCnt;
    @Column(name = "data_cnt")
    private Integer dataCnt;
    @Column(name = "enable")
    private Integer enable;
    @Column(name = "expire")
    private Integer expire;
    @Column(name = "add_uid")
    private Integer addUid;
    @Column(name = "editUid")
    private Integer editUid;
    @Column(name = "add_username")
    private String addUsername;
    @Column(name = "edit_username")
    private String editUsername;
    @Column(name = "gmt_create")
    private Integer gmtCreate;
    @Column(name = "gmt_modify")
    private Integer gmtModify;

    @JsonBackReference
    @ManyToMany(mappedBy = "authorities")
    private List<Role> roles;


    //省略了getter和setter方法
}
