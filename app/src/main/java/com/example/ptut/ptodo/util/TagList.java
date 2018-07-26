package com.example.ptut.ptodo.util;


/**
 * Created by Ptut on 2/27/2018.
 */

public class TagList {

    private String code;
    private String name;


    public TagList() {

    }

    public TagList(String sinif, String name) {
        this.code = sinif;
        this.name = name;


    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getSinif() {
        return code;
    }

    public void setSinif(String code) {
        this.code = code;
    }


}
