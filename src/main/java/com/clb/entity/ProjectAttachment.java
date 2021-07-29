package com.clb.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
public class ProjectAttachment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String categroy;
    private int refId;
    private String fileName;
    private String fileUrl;
    private int fileSize;
    private String fileExt;
    private Integer addUid;
    private String addUsername;
    private Integer editUid;
    private String editUsername;
    private String pFolder;
    private int fileUploadtime;

    public String getUploadTime(){
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        return ft.format((float)getFileUploadtime()*1000);
    }

}
