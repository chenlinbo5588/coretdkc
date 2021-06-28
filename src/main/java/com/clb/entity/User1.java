package com.clb.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "user_tabel")
public class User1 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "status")
    private Integer status;
    @Column(name = "enable")
    private Integer enable;
    @Column(name = "mobile")
    private String mobile;
    @Column(name = "sex")
    private Integer sex;
    @Column(name = "nickname")
    private String nickname;
    @Column(name = "email_status")
    private String emailStatus;
    @Column(name = "last_login")
    private Integer lastLogin;
    @Column(name = "last_loginip")
    private String lastLoginip;
    @Column(name = "sid")
    private String sid;
    @Column(name = "pm_id")
    private Integer pmId;
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

    public User1() {
        this.userId = userId;
        this.userName = userName;
        this.roles = roles;
    }

    //关键点
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_role", //name是表名
            //joinColumns设置的是entity中属性到关系表的映射名称，name是映射表中的字段名
            joinColumns = {@JoinColumn(name = "user_id")},
            //inverseJoinColumns,name是关系实体Role的id在关系表中的名称
            inverseJoinColumns = {@JoinColumn(name = "role_id")}
    )
    private List<Role> roles;

    //省略了getter和setter方法
}