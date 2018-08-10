package com.bs.utown.seruser;

import android.os.Bundle;
import android.view.View;

import com.bs.utown.R;
import com.bs.utown.base.BaseActivity;

public class PayRecordActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_record);
        baseapp.addActivity(this);
        initView();
    }

    private void initView() {
        findViewById(R.id.back_payrecord).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_payrecord:
                baseapp.finishActivity();
                break;
        }
    }
}
