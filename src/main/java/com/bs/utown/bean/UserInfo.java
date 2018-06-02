package com.bs.utown.bean;

import java.io.Serializable;

/**
 * Description: user信息的bean类
 * AUTHOR: Champion Dragon
 * created at 2018/6/1
 **/

public class UserInfo implements Serializable{
    String headpath;

    public void setHeadpath(String headpath) {
        this.headpath = headpath;
    }

    public String getHeadpath() {
        return headpath;
    }
}
