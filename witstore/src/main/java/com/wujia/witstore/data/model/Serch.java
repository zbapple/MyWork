package com.wujia.witstore.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ZOUBIN on 2015/9/17.
 * 10:50
 */
public class Serch {


    private int id;
    @SerializedName("d_type")
    private String type;
    private String name;
    @SerializedName("w_name")
    private String wname;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWname() {
        return wname;
    }

    public void setWname(String wname) {
        this.wname = wname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
