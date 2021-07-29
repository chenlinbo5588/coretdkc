package com.clb.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
public class FileUpload {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer fileId;
    private String fileName;
    private String fileUrl;
    private String pname;
    private Integer fileSize;
    private Integer uploadTime;
    private Integer gmtAdd;
    private Integer addUid;
    private String addUsername;
    private Integer gmtEdit;
    private Integer editUid;
    private String editUsername;
    private Integer pid;


}
