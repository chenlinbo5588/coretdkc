package com.clb.dto;

import lombok.Data;

import java.util.List;

@Data
public class GdInfo {

    private Integer count;
    private String info;
    List<PoiInfo> tips;

}
