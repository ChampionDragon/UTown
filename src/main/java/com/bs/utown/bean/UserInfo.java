package com.bs.utown.bean;

import java.io.Serializable;

/**
 * Description: user信息的bean类
 * AUTHOR: Champion Dragon
 * created at 2018/6/1
 **/

public class UserInfo implements Serializable{
    private String headpath;
    private String name,UID,code,gender,birthday;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setHeadpath(String headpath) {
        this.headpath = headpath;
    }

    public String getHeadpath() {
        return headpath;
    }
}
