package com.bs.utown.bean;

import java.io.Serializable;

/**
 * Description: 申请的bean类
 * AUTHOR: Champion Dragon
 * created at 2018/6/11
 **/

public class ApplyBean implements Serializable {
    private String applyStr;
    private String applyRs;
    private String applyTime;
    private String name;
    private String user;
    private String phone;
    private String desc;//备注描述
    private String email;
    private String enterNum;//入驻人数

    public String getEnterNum() {
        return enterNum;
    }

    public void setEnterNum(String enterNum) {
        this.enterNum = enterNum;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

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

    public String getApplyStr() {
        return applyStr;
    }

    public void setApplyStr(String applyStr) {
        this.applyStr = applyStr;
    }

    public String getApplyRs() {
        return applyRs;
    }

    public void setApplyRs(String applyRs) {
        this.applyRs = applyRs;
    }

    public String getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(String applyTime) {
        this.applyTime = applyTime;
    }
}
