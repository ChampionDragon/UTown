package com.bs.utown.constant;

import android.os.Environment;

import com.bs.utown.util.SystemUtil;

import java.io.File;

/**
 * Description: 常量类
 * AUTHOR: Champion Dragon
 * created at 2018/5/28
 **/

public class Constant {
    /* 文件夹 */
    public final static String fileRoot = SystemUtil.AppName();//app名字
    public final static File fileDir = new File(Environment.getExternalStorageDirectory(), fileRoot);// 整个项目的目录
    public final static String PathDir= fileDir.toString();// 整个项目的路径
    public final static String license = "营业执照";
    public final static String filehead = "头像";
    public final static String temp = "缓存";


    /* 时间格式 */
    public final static String cformatDay = "yyyy年MM月dd日";
    public final static String cformatD = "M月d日";
    public final static String cformatsecond = "yyyy年MM月dd日HH时mm分ss秒";
    public final static String formatminute = "HH:mm";
    public final static String formatsecond = "yyyy-MM-dd HH:mm:ss";
    public final static String formatlogin = "yyyy/MM/dd HH:mm";//登录记录的格式
    public final static String formatnotify = "yyyy/MM/dd HH:mm:ss";//通知记录的格式
    public final static String formatbusinesstime = "yyyy/MM/dd";//通知记录的格式
    public final static int Day=24*60*60*1000;
}
