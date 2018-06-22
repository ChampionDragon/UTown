package com.bs.utown.bean;

import java.util.ArrayList;

/**
 * Description: 所需数据的集合
 * AUTHOR: Champion Dragon
 * created at 2018/6/22
 **/
public class DataBean {
    /*颜色的集合*/
    public static ArrayList<String> getColor() {
        ArrayList<String> a = new ArrayList();
        a.add("黑");
        a.add("红");
        a.add("蓝");
        a.add("白");
        a.add("灰");
        a.add("银");
        a.add("紫");
        a.add("黄");
        a.add("橙");
        a.add("绿");
        return a;
    }

    /*汽车品牌的集合*/
    public static ArrayList<String> getCar() {
        ArrayList<String> a = new ArrayList();
        a.add("宝马");
        a.add("奔驰");
        a.add("奥迪");
        a.add("大众");
        a.add("哈弗");
        a.add("WEY");
        a.add("福特");
        a.add("长安");
        a.add("长城");
        a.add("吉利");
        a.add("比亚迪");
        a.add("江铃");
        a.add("标志");
        a.add("凯迪拉克");
        a.add("通用");
        a.add("雪佛兰");
        a.add("沃尔沃");
        a.add("起亚");
        a.add("现代");
        a.add("本田");
        a.add("丰田");
        a.add("日产");
        a.add("马自达");
        a.add("铃木");
        return a;
    }
}
