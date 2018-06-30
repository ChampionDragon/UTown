package com.bs.utown.sercompany;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bs.utown.R;
import com.bs.utown.base.BaseActivity;
import com.bs.utown.constant.Constant;
import com.bs.utown.listener.DiadisListener;
import com.bs.utown.pickerview.TimePickerView;
import com.bs.utown.pickerview.other.pickerViewUtil;
import com.bs.utown.util.SmallUtil;
import com.bs.utown.util.ToastUtil;
import com.bs.utown.view.DialogIv;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Description: 申请入驻
 * AUTHOR: Champion Dragon
 * created at 2018/6/27
 **/
public class AdmissionApplyActivity extends BaseActivity implements View.OnClickListener {
    private TextView time, type, bussiness;
    private EditText name, user, phone, email;
    private DialogIv dialogIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admission_apply);
        baseapp.TemaddActivity(this);
        initView();
    }

    private void initView() {
        findViewById(R.id.back_admissionapply).setOnClickListener(this);
        findViewById(R.id.admissionapply_next).setOnClickListener(this);
        findViewById(R.id.admissionapply_explanation).setOnClickListener(this);
        findViewById(R.id.admissionapply_time).setOnClickListener(this);
        time = (TextView) findViewById(R.id.admissionapply_timetv);
        name = (EditText) findViewById(R.id.admissionapply_name);
        user = (EditText) findViewById(R.id.admissionapply_user);
        phone = (EditText) findViewById(R.id.admissionapply_phone);
        email = (EditText) findViewById(R.id.admissionapply_email);
        type = (TextView) findViewById(R.id.admissionapply_type);
        bussiness = (TextView) findViewById(R.id.admissionapply_bussiness);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_admissionapply:
                baseapp.TemfinishActivity(this);
                break;
            case R.id.admissionapply_explanation:
                OpenDialog();
                break;
            case R.id.admissionapply_time:
                Time();
                break;
            case R.id.admissionapply_next:
                Next();
                break;
        }
    }


    //创建二维码弹框
    private void OpenDialog() {
        if (dialogIv != null) {

        } else {
            dialogIv = new DialogIv(this, R.mipmap.explan, new DiadisListener() {
                @Override
                public void dismiss() {
                    closeDialog();
                }
            });
        }
    }

    //关闭
    private void closeDialog() {
        if (dialogIv != null) {
            dialogIv.closeDia();
            dialogIv = null;
        }
    }


    private void Time() {
        pickerViewUtil.alertTimerPicker(AdmissionApplyActivity.this, TimePickerView.Type.YEAR_MONTH_DAY, Constant.formatbusinesstime,
                "请选择入驻时间", 19, new pickerViewUtil.TimerPickerCallBack() {
                    @Override
                    public void onTimeSelect(String date) {
                        time.setText(date);
                    }
                });
    }

    private void Next() {
        if (!checkEmail(email.getText().toString())) {
            ToastUtil.showLong("输入的邮箱格式不对");
            return;
        }
        SmallUtil.getActivity(AdmissionApplyActivity.this,AdmissionLicenseActivity.class);
    }

    /* 验证邮箱*/
    public boolean checkEmail(String email) {
        boolean flag = false;
        try {
            String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

}
