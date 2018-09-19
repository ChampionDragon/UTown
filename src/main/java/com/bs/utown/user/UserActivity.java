package com.bs.utown.user;

import android.app.Dialog;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bs.utown.R;
import com.bs.utown.account.LoginActivity;
import com.bs.utown.base.BaseActivity;
import com.bs.utown.bean.UserInfo;
import com.bs.utown.constant.SpKey;
import com.bs.utown.util.ObjectSave;
import com.bs.utown.util.SmallUtil;
import com.bs.utown.view.DialogCustomUtil;
import com.bs.utown.view.RoundImageView;

import java.io.File;

/**
 * Description: 个人中心
 * AUTHOR: Champion Dragon
 * created at 2018/6/1
 **/
public class UserActivity extends BaseActivity implements View.OnClickListener {
    private RoundImageView head;
    private Dialog dialog;
    private TextView userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseapp.addActivity(this);
        setContentView(R.layout.activity_user);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        /*初始化头像*/
        UserInfo userInfo = ObjectSave.getUserInfo();
        String headPath = userInfo.getHeadpath();
        String names = userInfo.getName();
        if (headPath != null && !headPath.isEmpty()) {
            File filehead = new File(headPath);
            Uri uri = Uri.fromFile(filehead);
            head.setImageURI(uri);
        }
        if (names != null && !names.isEmpty()) {
            userName.setText(names);
        }
    }

    private void initView() {
        head = (RoundImageView) findViewById(R.id.user_riv);
        head.setOnClickListener(this);
        findViewById(R.id.user_back).setOnClickListener(this);
        findViewById(R.id.user_out).setOnClickListener(this);
        findViewById(R.id.user_apply).setOnClickListener(this);
        findViewById(R.id.user_act).setOnClickListener(this);
        findViewById(R.id.user_reservation).setOnClickListener(this);
        findViewById(R.id.user_office).setOnClickListener(this);
        findViewById(R.id.user_car).setOnClickListener(this);
        userName = (TextView) findViewById(R.id.user_name);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_riv:
                SmallUtil.getActivity(UserActivity.this, UserMsgActivity.class);
                break;
            case R.id.user_car:
                SmallUtil.getActivity(UserActivity.this, UserCarmgrActivity.class);
                break;
            case R.id.user_act:
                SmallUtil.getActivity(UserActivity.this, UserActActivity.class);
                break;
            case R.id.user_apply:
                SmallUtil.getActivity(UserActivity.this, UserApplyActivity.class);
                break;
            case R.id.user_reservation:
                SmallUtil.getActivity(UserActivity.this, UserResnActivity.class);
                break;
            case R.id.user_office:
                SmallUtil.getActivity(UserActivity.this, UserOfficeActivity.class);
                break;
            case R.id.user_back:
                baseapp.finishActivity();
                break;
            case R.id.user_out:
                dialog = DialogCustomUtil.create("警告", "您确定要退出？",
                        UserActivity.this, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                                spUser.putBoolean(SpKey.isLogin, false);
                                SmallUtil.getActivity(UserActivity.this, LoginActivity.class);
                                Dataclean();//清除数据
                                baseapp.finishAllActivity();
                            }
                        });
                dialog.show();
                break;
        }
    }
}
