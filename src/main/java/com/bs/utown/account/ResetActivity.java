package com.bs.utown.account;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bs.utown.R;
import com.bs.utown.base.BaseActivity;
import com.bs.utown.util.SmallUtil;
import com.bs.utown.util.ToastUtil;

public class ResetActivity extends BaseActivity implements View.OnClickListener {
    private EditText phone, code;
    private TextView codeGet;
    private TimeCount timeCount;
    private boolean codeCheck = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseapp.TemaddActivity(this);
        setContentView(R.layout.activity_reset);
        initView();
        timeCount = new TimeCount(60000, 1000);
    }

    private void initView() {
        findViewById(R.id.reset_back).setOnClickListener(this);
        findViewById(R.id.reset_confirm).setOnClickListener(this);
        phone = (EditText) findViewById(R.id.reset_phone);
        code = (EditText) findViewById(R.id.reset_code);
        codeGet = (TextView) findViewById(R.id.reset_codetv);
        codeGet.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.reset_back:
                baseapp.TemfinishActivity(this);
                break;
            case R.id.reset_confirm:
                registerCheck();
                break;
            case R.id.reset_codetv:
                codeCheck();
                break;
        }
    }


    /*验证格式*/
    private void registerCheck() {
        String phoneStr = phone.getText().toString();
        String codeString = code.getText().toString();
        boolean code = TextUtils.isEmpty(codeString);
        boolean checkphone = TextUtils.isEmpty(phoneStr);
        if (checkphone) {
            ToastUtil.showLong("手机号码不能为空");
            return;
        } else if (phoneStr.length() != 11) {
            ToastUtil.showLong("手机不是11位");
            return;
        }
        if (code) {
            ToastUtil.showLong("验证码不能为空");
            return;
        } else if (codeString.length() != 6) {
            ToastUtil.showLong("验证码不是6位");
            return;
        }
        SmallUtil.getActivity(ResetActivity.this, PwdActivity.class);
    }



    /*验证码格式验证*/
    private void codeCheck() {
        if (codeCheck) {
            String phoneStr = phone.getText().toString();
            boolean checkphone = TextUtils.isEmpty(phoneStr);
            if (checkphone) {
                ToastUtil.showLong("手机号码不能为空");
                return;
            } else if (phoneStr.length() != 11) {
                ToastUtil.showLong("手机格式不对");
                return;
            }
//            executor.submit(codeRunnable);
            timeCount.start();
        }
    }





    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            codeCheck = false;
            codeGet.setText(millisUntilFinished / 1000 + "s后重发");
        }

        @Override
        public void onFinish() {
            codeCheck = true;
            codeGet.setText("获取验证码");
        }

    }


}
