package com.bs.utown.bean;

import java.io.Serializable;

/**
 * Description: 预定的Bean类
 * AUTHOR: Champion Dragon
 * created at 2018/6/14
 **/

public class ResnBean implements Serializable {
    private String name;//房间名字
    private String price;//价格
    private String priceUnit;//价格单位    0:元/月   1:元/年
    private String equipment;//设备
    private String place;//地点
    private String time;//时间
    private String url;//图片地址
    private String num;//人数

    public String getNumUnit() {
        return numUnit;
    }

    public void setNumUnit(String numUnit) {
        this.numUnit = numUnit;
    }

    private String numUnit;//人数         0:可容纳人数 1:剩余工位
    private String area;//面积

    public String getPriceUnit() {
        return priceUnit;
    }

    public void setPriceUnit(String priceUnit) {
        this.priceUnit = priceUnit;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
}
