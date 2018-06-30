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
    public final static String PathDir = fileDir.toString();// 整个项目的路径
    public final static String license = "营业执照";
    public final static String filehead = "头像";
    public final static String temp = "缓存";


    /* 登录模块 */
    public final static String Login = "http://www.bsznyun.com/wifi/get_user_wx.php";//登录
    public final static String mobileMsg = "http://www.bsznyun.com/simplewind/Core/Library/Vendor/alidayu/sendmsg.php";//短信
    public final static String register = "http://www.bsznyun.com/interface/register.php";//注册
    public final static String reset = "http://www.bsznyun.com/interface/forget.php";//修改密码


    /* 时间格式 */
    public final static String cformatymd = "yyyy年MM月dd日";
    public final static String cformatmd = "M月d日";
    public final static String cformatsecond = "yyyy年MM月dd日HH时mm分ss秒";
    public final static String formathm = "HH:mm";
    public final static String formatsecond = "yyyy-MM-dd HH:mm:ss";
    public final static String formatlogin = "yyyy/MM/dd HH:mm";//登录记录的格式
    public final static String formatnotify = "yyyy/MM/dd HH:mm:ss";//预定时间的格式
    public final static String formatbusinesstime = "yyyy/MM/dd";//
    public final static String formatresn = "M月d日 E";//选择日期格式
    public final static long Day = 24 * 60 * 60 * 1000;
    public final static long halfHour = 30 * 60 * 1000;




    /* +++++++++++++++++++++++ 第三方KEY ++++++++++++++++++++++++++++++*/
    /**
     * app_id是从微信官网申请到的合法APPid
     */
    public static final String APP_ID_WX = "wx1a03d6d2abbc5b6a";

    /**
     * 微信AppSecret值
     */
    public static final String APP_SECRET_WX = "831ec926826acb588984ef1071ab902e";


    /* +++++++++++++++++++++++ 地址 ++++++++++++++++++++++++++++++*/
    public static final String url_1 = "http://img0.imgtn.bdimg.com/it/u=425164315,2803969613&fm=27&gp=0.jpg";
    public static final String url_2 = "http://img4.imgtn.bdimg.com/it/u=3996175993,1337165617&fm=27&gp=0.jpg";
    public static final String url_3 = "http://img4.imgtn.bdimg.com/it/u=3831423996,1610400728&fm=27&gp=0.jpg";
    public final static String urlBaiSheng = "http://www.bisensa.com/";
    public static final String url_admission = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1530092016299&di=b13e4933d33bfad3ea63e26899ca8e1e&imgtype=jpg&src=http%3A%2F%2Fimg3.imgtn.bdimg.com%2Fit%2Fu%3D2237573153%2C1502960089%26fm%3D214%26gp%3D0.jpg";


}
