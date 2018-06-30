package com.bs.utown.sercompany;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bs.utown.R;
import com.bs.utown.base.BaseActivity;

/**
 * Description: 上传营业执照
 * AUTHOR: Champion Dragon
 * created at 2018/6/30
 **/
public class AdmissionLicenseActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admission_license);
        baseapp.TemaddActivity(this);
        initView();
    }

    private void initView() {
        findViewById(R.id.admissionlicense_iv).setOnClickListener(this);
        findViewById(R.id.back_admissionlicense).setOnClickListener(this);
        iv = (ImageView) findViewById(R.id.admissionlicense_iv);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.admissionlicense_iv:
                iv.setImageResource(R.mipmap.logo);
                break;
            case R.id.back_admissionlicense:
                baseapp.TemfinishActivity(this);
                break;
        }
    }
}
