package com.bs.utown.bean;

import java.io.Serializable;

/**
 * Description: 车的Bean类
 * AUTHOR: Champion Dragon
 * created at 2018/6/21
 **/

public class CarBean implements Serializable{
    private String name,plateNum,color;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlateNum() {
        return plateNum;
    }

    public void setPlateNum(String plateNum) {
        this.plateNum = plateNum;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
