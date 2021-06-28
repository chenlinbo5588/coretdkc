package com.clb.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "role_table")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Integer roleId;
    @Column(name = "role_name")
    private String roleName;
    @Column(name = "permission")
    private Integer permission;
    @Column(name = "enable")
    private Integer enable;
    @Column(name = "expire")
    private Integer expire;
    @Column(name = "user_cnt")
    private Integer user_cnt;
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


    //作为被维护端，只需要设置mappedBy属性，其值与User中对应List类型变量名相同
    //@JsonBackReference可以避免属性被json序列化，出现死循环
    @JsonBackReference
    @ManyToMany(mappedBy = "roles")
    private List<User1> users;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "role_auth", //name是表名
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns =@JoinColumn(name = "auth_id")
    )
    private List<Authority> authorities;

    //省略了getter和setter方法
}
