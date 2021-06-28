package com.clb.entity;

public class RiverType {

    private String type;

    private String select;

    public RiverType() {
    }

    public RiverType(String type, String select) {
        this.type = type;
        this.select = select;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSelect() {
        return select;
    }

    public void setSelect(String select) {
        this.select = select;
    }
}
