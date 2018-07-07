package com.bs.utown.sercompany;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bs.utown.R;
import com.bs.utown.base.BaseActivity;
import com.bs.utown.bean.AdmissionBean;
import com.bs.utown.constant.SpKey;
import com.bs.utown.util.SmallUtil;

import java.io.File;

/**
 * Description:提交申请
 * AUTHOR: Champion Dragon
 * created at 2018/7/6
 **/
public class AdmissionSubmitActivity extends BaseActivity implements View.OnClickListener {
    private TextView name, time, user, phone, email, type, bussiness, tv;
    private ImageView iv;
    private File image;
    private String tag = "AdmissionSubmitActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admission_submit);
        baseapp.TemaddActivity(this);
        initView();
    }

    private void initView() {
        findViewById(R.id.back_admissionsubmit).setOnClickListener(this);
        findViewById(R.id.admissionsubmit_submit).setOnClickListener(this);
        name = (TextView) findViewById(R.id.admissionsubmit_name);
        time = (TextView) findViewById(R.id.admissionsubmit_time);
        user = (TextView) findViewById(R.id.admissionsubmit_user);
        phone = (TextView) findViewById(R.id.admissionsubmit_phone);
        email = (TextView) findViewById(R.id.admissionsubmit_email);
        type = (TextView) findViewById(R.id.admissionsubmit_type);
        bussiness = (TextView) findViewById(R.id.admissionsubmit_bussiness);
        tv = (TextView) findViewById(R.id.admissionsubmit_tv);
        iv = (ImageView) findViewById(R.id.admissionsubmit_iv);

        Bundle bundle = getIntent().getExtras();
        AdmissionBean bean = (AdmissionBean) bundle.getSerializable(SpKey.admissionBean);
        name.setText(bean.getName());
        time.setText(bean.getTime());
        user.setText(bean.getUser());
        phone.setText(bean.getPhone());
        email.setText(bean.getEmail());
        type.setText(bean.getType());
        bussiness.setText(bean.getBussiness());


        image = new File(baseapp.sp.getString(SpKey.licensePath));
        if (image.exists()) {
            iv.setImageBitmap(BitmapFactory.decodeFile(image.getAbsolutePath()));
            tv.setVisibility(View.GONE);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_admissionsubmit:
                baseapp.TemfinishActivity(this);
                break;
            case R.id.admissionsubmit_submit:
                Submit();
                break;
        }
    }

    /*提交后的一些操作*/
    private void Submit() {
        if (image.exists()) {
            image.delete();
        }
        SmallUtil.getActivity(AdmissionSubmitActivity.this,AdmissionSuccessActivity.class);
    }


}
