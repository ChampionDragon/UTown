package com.bs.utown;

import android.os.Bundle;
import android.view.View;

import com.bs.utown.base.BaseActivity;
import com.bs.utown.sercompany.ResnsiteActivity;
import com.bs.utown.util.SmallUtil;

/**
 * Description: 所有服务的主页
 * AUTHOR: Champion Dragon
 * created at 2018/5/31
 **/
public class SerAllActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseapp.addActivity(this);
        setContentView(R.layout.activity_ser_all);
        initView();
    }

    private void initView() {
        findViewById(R.id.back_serall).setOnClickListener(this);
        findViewById(R.id.serall_resnsite).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_serall:
                baseapp.finishActivity();
                break;
            case R.id.serall_resnsite:
                SmallUtil.getActivity(SerAllActivity.this, ResnsiteActivity.class);
                break;
        }
    }
}
