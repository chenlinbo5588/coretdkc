package com.clb.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class FxTableList {

        String name;
        String type;
        List<String> data;

    public FxTableList() {
        this.name = null;
        this.type = null;
        this.data = new ArrayList<String>();
    }
}
