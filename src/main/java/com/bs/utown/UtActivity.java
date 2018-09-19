package com.bs.utown;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;

import com.bs.utown.base.BaseActivity;
import com.bs.utown.bean.UserInfo;
import com.bs.utown.listener.DiadisListener;
import com.bs.utown.user.UserActivity;
import com.bs.utown.util.ObjectSave;
import com.bs.utown.util.SmallUtil;
import com.bs.utown.view.DialogOpen;
import com.bs.utown.view.RoundImageView;

/**
 * Description: 主界面
 * AUTHOR: Champion Dragon
 * created at 2018/8/4
 **/
public class UtActivity extends BaseActivity implements View.OnClickListener {
    private DialogOpen diaOpen;//开门的弹框
    private RoundImageView head;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ut);
        baseapp.addActivity(this);
        initView();
        test();//测试
    }


    private void test() {

    }


    @Override
    protected void onResume() {
        super.onResume();
              /*初始化头像*/
        UserInfo userInfo = ObjectSave.getUserInfo();
        String headPath = userInfo.getHeadpath();
        if (headPath != null && !headPath.isEmpty()) {
            head.setImageBitmap(BitmapFactory.decodeFile(headPath));
        }
    }

    private void initView() {
        head = (RoundImageView) findViewById(R.id.ut_head);
        head.setOnClickListener(this);
        findViewById(R.id.ut_code).setOnClickListener(this);
        findViewById(R.id.ut_server).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ut_head:
                SmallUtil.getActivity(UtActivity.this, UserActivity.class);
                break;
            case R.id.ut_code:
                OpenDialog();
                break;
            case R.id.ut_server:
                SmallUtil.getActivity(UtActivity.this, SerAllActivity.class);
                break;
        }
    }


    /* ++++++++++++++++++++++++++++++++++++++++++++++++++++++     开门二维码对话框     ++++++++++++++++++++++++++++++++++++++++++++++*/
    //创建
    private void OpenDialog() {
        if (diaOpen != null) {

        } else {
            diaOpen = new DialogOpen(this, new DiadisListener() {
                @Override
                public void dismiss(Object object) {
                    closeDialog();
                }
            });
        }
    }

    //更新二维码
    private void UpdateCode(String code) {
        if (diaOpen != null) {
            diaOpen.updateCode(code);
        }
    }

    //关闭
    private void closeDialog() {
        if (diaOpen != null) {
            diaOpen.closeDia();
            diaOpen = null;
        }
    }


}
