package com.bs.utown.user;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;

import com.bs.utown.R;
import com.bs.utown.account.LoginActivity;
import com.bs.utown.base.BaseActivity;
import com.bs.utown.constant.SpKey;
import com.bs.utown.util.SmallUtil;
import com.bs.utown.view.DialogCustomUtil;
import com.bs.utown.view.RoundImageView;

/**
 * Description: 个人中心
 * AUTHOR: Champion Dragon
 * created at 2018/6/1
 **/
public class UserActivity extends BaseActivity implements View.OnClickListener {
    private RoundImageView head;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseapp.addActivity(this);
        setContentView(R.layout.activity_user);
        initView();
    }

    private void initView() {
        head = (RoundImageView) findViewById(R.id.user_riv);
        head.setOnClickListener(this);
        findViewById(R.id.user_back).setOnClickListener(this);
        findViewById(R.id.user_out).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_riv:
                SmallUtil.getActivity(UserActivity.this,UserMsgActivity.class);
                break;
            case R.id.user_back:
                baseapp.finishActivity();
                break;
            case R.id.user_out:
                dialog = DialogCustomUtil.create("警告", "您确定要退出",
                        UserActivity.this, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                                spUser.putBoolean(SpKey.isLogin, false);
                                SmallUtil.getActivity(UserActivity.this, LoginActivity.class);
                                baseapp.finishAllActivity();
                            }
                        });
                dialog.show();
                break;
        }
    }


}
