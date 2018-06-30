package com.bs.utown.bean;

import java.io.Serializable;

/**
 * Description:公告的bean类
 * AUTHOR: Champion Dragon
 * created at 2018/6/27
 **/

public class NoticeBean implements Serializable{
    private String msg,time;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
