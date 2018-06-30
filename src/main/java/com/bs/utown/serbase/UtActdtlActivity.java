package com.bs.utown.serbase;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bs.utown.R;
import com.bs.utown.base.BaseActivity;
import com.bs.utown.bean.ActBean;
import com.bs.utown.constant.SpKey;
import com.squareup.picasso.Picasso;

public class UtActdtlActivity extends BaseActivity implements View.OnClickListener {
    private TextView name, nameTv, time, place, quota, realse;
    private ImageView iv;
    private String tag = "UtActdtlActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ut_actdtl);
        baseapp.addActivity(this);
        initView();
        initData();
    }

    private void initData() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            ActBean bean = (ActBean) extras.getSerializable(SpKey.actBean);
            name.setText(bean.getName());
            nameTv.setText(bean.getName());
            time.setText(bean.getTime());
            place.setText(bean.getPlace());
            Picasso.with(this).load(bean.getUrl()).placeholder(R.mipmap.logo).error(R.mipmap.logo).into(iv);
        }
    }

    private void initView() {
        name = (TextView) findViewById(R.id.utactdtl_name);
        nameTv = (TextView) findViewById(R.id.utactdtl_nametv);
        time = (TextView) findViewById(R.id.utactdtl_time);
        place = (TextView) findViewById(R.id.utactdtl_place);
        quota = (TextView) findViewById(R.id.utactdtl_quota);
        realse = (TextView) findViewById(R.id.quota_realse);
        iv = (ImageView) findViewById(R.id.utactdtl_iv);
        findViewById(R.id.utactdtl_apply).setOnClickListener(this);
        findViewById(R.id.back_utactdtl).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.utactdtl_apply:
                baseapp.finishActivity();
                break;
            case R.id.back_utactdtl:
                baseapp.finishActivity();
                break;
        }
    }


}
