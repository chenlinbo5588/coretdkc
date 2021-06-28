package com.clb.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "file_upload")
public class FileUpload {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id")
    private Integer fileId;
    @Column(name = "file_name")
    private String fileName;
    @Column(name = "file_url")
    private String fileUrl;
    @Column(name = "pid")
    private Integer Pid;
    @Column(name = "pname")
    private String Pname;
    @Column(name = "file_size")
    private Integer fileSize;
    @Column(name = "upload_time")
    private Integer uploadTime;
    @Column(name = "gmt_add")
    private Integer gmtAdd;
    @Column(name = "add_uid")
    private Integer addUid;
    @Column(name = "add_username")
    private String addUsername;
    @Column(name = "gmt_edit")
    private Integer gmtEdit;
    @Column(name = "edit_uid")
    private Integer editUid;
    @Column(name = "edit_username")
    private String editUsername;

}
