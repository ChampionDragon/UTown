package com.bs.utown;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bs.utown.base.BaseActivity;
import com.bs.utown.bean.UserInfo;
import com.bs.utown.listener.DiadisListener;
import com.bs.utown.user.UserActivity;
import com.bs.utown.util.ObjectSave;
import com.bs.utown.util.SmallUtil;
import com.bs.utown.util.ToastUtil;
import com.bs.utown.view.DialogOpen;
import com.bs.utown.view.RoundImageView;

/**
 * Description: APP的主页
 * AUTHOR: Champion Dragon
 * created at 2018/5/31
 **/
public class MainActivity extends BaseActivity implements View.OnClickListener {
    private ImageView serall;
    private DialogOpen diaOpen;//开门的弹框
    private RoundImageView head;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseapp.addActivity(this);
        setContentView(R.layout.activity_main);
        initView();
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
        findViewById(R.id.main_serall).setOnClickListener(this);
        findViewById(R.id.main_open).setOnClickListener(this);
        head= (RoundImageView) findViewById(R.id.main_head);
        head.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_serall:
                SmallUtil.getActivity(MainActivity.this, SerAllActivity.class);
                break;
            case R.id.main_open:
                OpenDialog();
                break;
            case R.id.main_head:
                SmallUtil.getActivity(MainActivity.this, UserActivity.class);
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
                public void dismiss() {
                    ToastUtil.showShort("关闭开门对话框");
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

