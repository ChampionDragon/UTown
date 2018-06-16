package com.bs.utown.bean;

import java.io.Serializable;

/**
 * Description: 预定的Bean类
 * AUTHOR: Champion Dragon
 * created at 2018/6/14
 **/

public class ResnBean implements Serializable{
    String name;
    String price;
    String equipment;
    String place;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    String url;

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
