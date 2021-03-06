package com.clb.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
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
    private long xcdate;
    private String xcjl;
    private String xchdUid;
    private Integer addUid;
    private String addUsername;
    private Integer reviseUid;
    private String reviseName;

    @Transient
    private Date xcdateS;

    public String getXcdateTime(){
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        return ft.format(getXcdate()*1000);
    }
}
