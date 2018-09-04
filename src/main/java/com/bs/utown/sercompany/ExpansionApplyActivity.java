package com.bs.utown.sercompany;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bs.utown.R;
import com.bs.utown.base.BaseActivity;
import com.bs.utown.constant.Constant;
import com.bs.utown.pickerview.TimePickerView;
import com.bs.utown.pickerview.other.pickerViewUtil;
import com.bs.utown.util.SmallUtil;
import com.bs.utown.util.ToastUtil;

/**
 * Description: 申请扩租
 * AUTHOR: Champion Dragon
 * created at 2018/7/12
 **/
public class ExpansionApplyActivity extends BaseActivity implements View.OnClickListener {
    private TextView name, time, user, phone, email, typearea;
    private EditText area;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expansion_apply);
        baseapp.TemaddActivity(this);
        initView();
    }

    private void initView() {
        findViewById(R.id.back_expansionapply).setOnClickListener(this);
        findViewById(R.id.expansion_time).setOnClickListener(this);
        findViewById(R.id.admissionapply_submit).setOnClickListener(this);
        name = (TextView) findViewById(R.id.expansion_name);
        time = (TextView) findViewById(R.id.expansion_timetv);
        user = (TextView) findViewById(R.id.expansion_user);
        phone = (TextView) findViewById(R.id.expansion_phone);
        email = (TextView) findViewById(R.id.expansion_email);
        typearea = (TextView) findViewById(R.id.expansion_typearea);
        area = (EditText) findViewById(R.id.expansion_area);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_expansionapply:
                baseapp.TemfinishActivity();
                break;
            case R.id.expansion_time:
                Time();
                break;
            case R.id.admissionapply_submit:
                Submit();
                break;
        }
    }

    private void Submit() {
        if(TextUtils.isEmpty(time.getText().toString())){
            ToastUtil.showLong("请输入\"扩租日期\"");
            return;
        }
        if(TextUtils.isEmpty(area.getText().toString())){
            ToastUtil.showLong("请输入\"扩租面积\"");
            return;
        }
        SmallUtil.getActivity(ExpansionApplyActivity.this, ExpansionSuccessActivity.class);
    }

    private void Time() {
        pickerViewUtil.alertTimerPicker(ExpansionApplyActivity.this, TimePickerView.Type.YEAR_MONTH_DAY, Constant.formatbusinesstime,
                "请选择入驻时间", 19, new pickerViewUtil.TimerPickerCallBack() {
                    @Override
                    public void onTimeSelect(String date) {
                        time.setText(date);
                    }
                });
    }


}
