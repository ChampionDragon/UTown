package com.bs.utown.bean;

import java.io.Serializable;

/**
 * Description: 入驻的Bean类
 * AUTHOR: Champion Dragon
 * created at 2018/7/5
 **/

public class AdmissionBean implements Serializable {
    private String name;
    private String user;
    private String phone;
    private String email;
    private String type;
    private String bussiness;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    private String time;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBussiness() {
        return bussiness;
    }

    public void setBussiness(String bussiness) {
        this.bussiness = bussiness;
    }
}
