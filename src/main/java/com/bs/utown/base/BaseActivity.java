package com.bs.utown.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.bs.utown.bean.UserInfo;
import com.bs.utown.constant.SpKey;
import com.bs.utown.util.ObjectSave;
import com.bs.utown.util.SmallUtil;
import com.bs.utown.util.SpUtil;

/**
 * Description: 创建程序的基本类
 * AUTHOR: Champion Dragon
 * created at 2018/5/28
 **/

public class BaseActivity extends AppCompatActivity {
    public AsyncTaskExecutor executor;
    public BaseApplication baseapp;
    public SpUtil spUser;
//    public DbManager managerDb;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*设置成透明导航栏和透明状态栏*/
        SmallUtil.setScreen(this);
        executor = AsyncTaskExecutor.getinstance();
        baseapp = BaseApplication.getInstance();
        spUser = SpUtil.getInstance(SpKey.SP_user, MODE_PRIVATE);
//        managerDb=baseapp.managerDb;
    }

    /*退出时的数据清除*/
    public void Dataclean() {
        UserInfo userInfo = new UserInfo();
        ObjectSave.SaveUserInfo(userInfo);
        spUser.putString(SpKey.UserPhone, null);
    }


}
