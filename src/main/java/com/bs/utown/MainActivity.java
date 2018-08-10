package com.bs.utown;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;

import com.bs.utown.base.BaseActivity;
import com.bs.utown.bean.UserInfo;
import com.bs.utown.listener.DiadisListener;
import com.bs.utown.serbase.UtActActivity;
import com.bs.utown.serbase.UtNewsActivity;
import com.bs.utown.serbase.UtNoticeActivity;
import com.bs.utown.sercompany.AdmissionCompActivity;
import com.bs.utown.sercompany.ResnsiteActivity;
import com.bs.utown.seruser.LifepayActivity;
import com.bs.utown.user.UserActivity;
import com.bs.utown.util.ObjectSave;
import com.bs.utown.util.SmallUtil;
import com.bs.utown.view.DialogOpen;
import com.bs.utown.view.RoundImageView;

/**
 * Description: APP的主页
 * AUTHOR: Champion Dragon
 * created at 2018/5/31
 **/
public class MainActivity extends BaseActivity implements View.OnClickListener {
    private DialogOpen diaOpen;//开门的弹框
    private RoundImageView head;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        baseapp.addActivity(this);
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
        findViewById(R.id.main_more).setOnClickListener(this);
        findViewById(R.id.main_open).setOnClickListener(this);
        findViewById(R.id.main_resnsite).setOnClickListener(this);
        findViewById(R.id.main_act).setOnClickListener(this);
        findViewById(R.id.main_news).setOnClickListener(this);//园区咨讯
        findViewById(R.id.main_admissioncomp).setOnClickListener(this);
        findViewById(R.id.main_lifepay).setOnClickListener(this);//生活缴费
        head = (RoundImageView) findViewById(R.id.main_head);
        head.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_serall:
                SmallUtil.getActivity(MainActivity.this, SerAllActivity.class);
                break;
            case R.id.main_lifepay:
                SmallUtil.getActivity(MainActivity.this, LifepayActivity.class);
                break;
            case R.id.main_admissioncomp:
                SmallUtil.getActivity(MainActivity.this, AdmissionCompActivity.class);
                break;
            case R.id.main_more:
                SmallUtil.getActivity(MainActivity.this, UtNoticeActivity.class);
                break;
            case R.id.main_news:
                SmallUtil.getActivity(MainActivity.this, UtNewsActivity.class);
                break;
            case R.id.main_act:
                SmallUtil.getActivity(MainActivity.this, UtActActivity.class);
                break;
            case R.id.main_resnsite:
                SmallUtil.getActivity(MainActivity.this, ResnsiteActivity.class);
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
                public void dismiss(Object object) {
//                    ToastUtil.showShort("关闭开门对话框");
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

