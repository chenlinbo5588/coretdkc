package com.clb.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class FxTableData {


    List<FxTableList> fxTableList;
    //增加减少总计
    String[] typeName;
    //横坐标值
    String[] xName;

    public FxTableData() {
        this.fxTableList = new ArrayList<FxTableList>();

    }
}
