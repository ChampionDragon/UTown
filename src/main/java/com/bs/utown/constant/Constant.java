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

   /* Utown数据*/
   public final static String Utownoffice = "http://www.bsznyun.com/interface/office_app.php";//共享办公
   public final static String Utownofficedetail = "http://www.bsznyun.com/interface/officedetail_app.php?id=";//共享办公详情
   public final static String Utownmeeting = "http://www.bsznyun.com/interface/meeting_app.php";//会议室
   public final static String Orderoffice = "http://www.bsznyun.com/interface/officeorder.php";//共享办公订单
   public final static String Ordermeeting = "http://www.bsznyun.com/interface/order.php";//会议室订单
   public final static String Ordermeetingdetail = "http://www.bsznyun.com/index.php?g=Api&m=Wechat&a=detail&id=";//会议室订单详情
   public final static String Apply = "http://www.bsznyun.com/index.php";//入驻申请
   public final static String ApplyInquire = "http://www.bsznyun.com/interface/applylist_app.php";//入驻申请查询




    /* 时间格式 */
    public final static String cformatymd = "yyyy年MM月dd日";
    public final static String cformatmd = "M月d日";
    public final static String cformatsecond = "yyyy年MM月dd日HH时mm分ss秒";
    public final static String formathm = "HH:mm";
    public final static String formatsecond = "yyyy-MM-dd HH:mm:ss";
    public final static String formatlogin = "yyyy/MM/dd HH:mm";//登录记录的格式
    public final static String formatnotify = "yyyy/MM/dd HH:mm:ss";//预定时间的格式
    public final static String formatbusinesstime = "yyyy-MM-dd";
    public final static String formatresn = "yy年M月d日 E";//选择日期格式
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
    public static final String url_1 = "http://www.bsznyun.com/data/upload/20180808/5b6a68fd0815f.png";
    public static final String url_2 = "http://www.bsznyun.com/data/upload/20180808/5b6a688c968c0.png";
    public static final String url_3 = "http://www.bsznyun.com/data/upload/20180808/5b6a6ab640fb0.png";
    public final static String urlBaiSheng = "http://www.bisensa.com/";//官网
    public final static String urlTerms = "http://weixin.qq.com/agreement?lang=zh_CN";//用户协议
    public static final String url_admission = "http://www.bsznyun.com/data/upload/20180808/5b6a6935607b9.png";


}
