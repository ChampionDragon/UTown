package com.bs.utown.sercompany;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bs.utown.R;
import com.bs.utown.base.BaseActivity;
import com.bs.utown.constant.Constant;
import com.bs.utown.constant.SpKey;
import com.bs.utown.pickerview.other.pickerViewUtil;
import com.bs.utown.util.TimeUtil;

import java.util.ArrayList;

/**
 * Description:预定的选择时间
 * AUTHOR: Champion Dragon
 * created at 2018/6/15
 **/
public class ResntimeActivity extends BaseActivity implements View.OnClickListener {
    private TextView day, start, end;
    private ArrayList<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resntime);
        initView();
    }

    private void initView() {
        findViewById(R.id.back_resntime).setOnClickListener(this);
        findViewById(R.id.resntime_confirm).setOnClickListener(this);
        day = (TextView) findViewById(R.id.resntime_day);
        start = (TextView) findViewById(R.id.resntime_start);
        end = (TextView) findViewById(R.id.resntime_end);
        day.setOnClickListener(this);
        start.setOnClickListener(this);
        end.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_resntime:
                finish();
                break;
            case R.id.resntime_day:
                day();
                break;
            case R.id.resntime_start:
                start();
                break;
            case R.id.resntime_end:
                end();
                break;
            case R.id.resntime_confirm:
                String time = day.getText().toString() + "  " + start.getText().toString() + "-" + end.getText().toString();
                setResult(ResndetailActivity.Resntime, getIntent().putExtra(SpKey.resnTime, time));
                finish();
                break;
        }
    }

    /*结束时间*/
    private void end() {
        String startStr = start.getText().toString();
        long startlong;
        if (startStr.contains(":")) {
            startlong = TimeUtil.time2long(startStr, Constant.formathm);
        } else {
            startlong = TimeUtil.time2long("8:00", Constant.formathm);
        }
        long endlong = TimeUtil.time2long("24:00", Constant.formathm);
        list = new ArrayList<>();
        for (long i = startlong; i < endlong; i += Constant.halfHour) {
            list.add(TimeUtil.long2time(i, Constant.formathm));
        }
        pickerViewUtil.alertBottomWheelOption(this, "结束时间", list, new pickerViewUtil.OnWheelViewClick() {
            @Override
            public void onClick(View view, int postion) {
                end.setText(list.get(postion));
            }
        });
    }


    /*开始时间*/
    private void start() {
        long startlong = TimeUtil.time2long("8:00", Constant.formathm);
        long end = TimeUtil.time2long("24:00", Constant.formathm);
        list = new ArrayList<>();
        for (long i = startlong; i < end; i += Constant.halfHour) {
            list.add(TimeUtil.long2time(i, Constant.formathm));
        }
        pickerViewUtil.alertBottomWheelOption(this, "开始时间", list, new pickerViewUtil.OnWheelViewClick() {
            @Override
            public void onClick(View view, int postion) {
                start.setText(list.get(postion));
            }
        });
    }

    /*选择日期*/
    private void day() {
        list = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            list.add(TimeUtil.long2time(System.currentTimeMillis() + Constant.Day * i, Constant.formatresn));
        }
        pickerViewUtil.alertBottomWheelOption(this, "选择日期", list, new pickerViewUtil.OnWheelViewClick() {
            @Override
            public void onClick(View view, int postion) {
                day.setText(list.get(postion));
            }
        });
    }


}
