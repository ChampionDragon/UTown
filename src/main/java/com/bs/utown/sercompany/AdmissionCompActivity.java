package com.bs.utown.sercompany;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bs.utown.R;
import com.bs.utown.base.BaseActivity;
import com.bs.utown.constant.Constant;
import com.bs.utown.user.UserApplyActivity;
import com.bs.utown.util.SmallUtil;
import com.squareup.picasso.Picasso;

/**
 * Description: 企业入驻
 * AUTHOR: Champion Dragon
 * created at 2018/6/27
 **/
public class AdmissionCompActivity extends BaseActivity implements View.OnClickListener {
    private ImageView apply, past;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admission_comp);
        baseapp.addActivity(this);
        initView();
    }

    private void initView() {
        findViewById(R.id.back_admissioncomp).setOnClickListener(this);
        apply = (ImageView) findViewById(R.id.admissioncomp_apply);
        apply.setOnClickListener(this);
        past = (ImageView) findViewById(R.id.admissioncomp_past);
        past.setOnClickListener(this);
        Picasso.with(this).load(Constant.url_admission).placeholder(R.mipmap.logo).error(R.mipmap.logo).into(apply);
        Picasso.with(this).load(Constant.url_1).placeholder(R.mipmap.logo).error(R.mipmap.logo).into(past);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_admissioncomp:
                baseapp.finishActivity();
                break;
            case R.id.admissioncomp_apply:
                SmallUtil.getActivity(AdmissionCompActivity.this, AdmissionApplyActivity.class);
                break;
            case R.id.admissioncomp_past:
                SmallUtil.getActivity(AdmissionCompActivity.this, UserApplyActivity.class);
                break;
        }
    }
}
