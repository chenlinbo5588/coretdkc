package com.clb.dto;

public class ArcgisTc {


    private String name;
    private String selectName;

    public ArcgisTc(String name, String selectName) {
        this.name = name;
        this.selectName = selectName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSelectName() {
        return selectName;
    }

    public void setSelectName(String selectName) {
        this.selectName = selectName;
    }
}
