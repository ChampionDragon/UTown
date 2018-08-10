package com.bs.utown.seruser;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bs.utown.R;
import com.bs.utown.base.BaseActivity;
import com.bs.utown.util.SmallUtil;

/**
 * Description: 生活缴费
 * AUTHOR: Champion Dragon
 * created at 2018/7/16
 **/
public class LifepayActivity extends BaseActivity implements View.OnClickListener {
    private TextView company, num, name, time, park, electricity, water, gas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lifepay);
        baseapp.addActivity(this);
        initView();
    }

    private void initView() {
        findViewById(R.id.lifepay_payment).setOnClickListener(this);
        findViewById(R.id.back_lifepay).setOnClickListener(this);
        findViewById(R.id.lifepay_record).setOnClickListener(this);
        company = (TextView) findViewById(R.id.lifepay_company);//缴费单位
        num = (TextView) findViewById(R.id.lifepay_num);//缴费户号
        name = (TextView) findViewById(R.id.lifepay_name);//户名
        time = (TextView) findViewById(R.id.lifepay_time);//缴费截止日期
        park = (TextView) findViewById(R.id.lifepay_parktv);//停车费
        electricity = (TextView) findViewById(R.id.lifepay_electricitytv);//电费
        water = (TextView) findViewById(R.id.lifepay_watertv);//水费
        gas = (TextView) findViewById(R.id.lifepay_gastv);//燃气费
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lifepay_payment:
                Pay();
                break;
            case R.id.back_lifepay:
                baseapp.finishActivity();
                break;
            case R.id.lifepay_record:
                SmallUtil.getActivity(LifepayActivity.this,PayRecordActivity.class);
                break;
        }
    }

    /**
     * 支付操作
     */
    private void Pay() {
        baseapp.finishActivity();
    }
}
