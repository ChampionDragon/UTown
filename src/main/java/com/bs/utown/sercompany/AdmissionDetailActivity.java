package com.bs.utown.sercompany;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bs.utown.R;
import com.bs.utown.base.BaseActivity;
import com.bs.utown.bean.ApplyBean;
import com.bs.utown.constant.SpKey;

/**
 * Description: 申请入驻详情
 * AUTHOR: Champion Dragon
 * created at 2018/8/14
 **/
public class AdmissionDetailActivity extends BaseActivity implements View.OnClickListener {
    private TextView name, time, user, phone, email;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admission_detail);
        baseapp.TemaddActivity(this);
        initView();
        initData();
    }

    private void initData() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            ApplyBean applyBean = (ApplyBean) extras.getSerializable(SpKey.applyBean);
            name.setText(applyBean.getName());
            time.setText(applyBean.getApplyTime());
            user.setText(applyBean.getUser());
            phone.setText(applyBean.getPhone());
            email.setText(applyBean.getEmail());
            if (!"通过".equals(applyBean.getApplyRs())) {
                button.setVisibility(View.GONE);
            }
        }
    }

    private void initView() {
        findViewById(R.id.back_admissiondetail).setOnClickListener(this);
        name = (TextView) findViewById(R.id.admissiondetail_name);
        time = (TextView) findViewById(R.id.admissiondetail_time);
        user = (TextView) findViewById(R.id.admissiondetail_user);
        phone = (TextView) findViewById(R.id.admissiondetail_phone);
        email = (TextView) findViewById(R.id.admissiondetail_email);
        button = (Button) findViewById(R.id.admissiondetail_choice);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_admissiondetail:
                baseapp.TemfinishActivity(this);
                break;
        }
    }


}
