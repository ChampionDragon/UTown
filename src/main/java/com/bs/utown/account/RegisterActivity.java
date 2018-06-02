package com.bs.utown.account;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bs.utown.R;
import com.bs.utown.base.BaseActivity;
import com.bs.utown.util.ToastUtil;
import com.bs.utown.view.DialogNotileUtil;

import java.util.Random;

/**
 * Description: 用户注册
 * AUTHOR: Champion Dragon
 * created at 2018/5/29
 **/
public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    private EditText phone, code, pwd, pwdConfirm, username;
    private TextView codeGet;
    private static final int REGISTER = 0;
    private static final int REGISTER_ERROR = 1;
    private static final int REGISTER_FAIL = 2;
    private static final int CODE = 3;
    private static final int CODE_ERROR = 4;
    private static final int CODE_FAIL = 5;
    private static final int RESULT_ERROR = 6;
    private boolean codeCheck = true;
    private TimeCount timeCount;
    private String codeError, registerError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        timeCount = new TimeCount(60000, 1000);
    }

    private void initView() {
        phone = (EditText) findViewById(R.id.register_phone);
        username = (EditText) findViewById(R.id.register_name);
        code = (EditText) findViewById(R.id.register_code);
        pwd = (EditText) findViewById(R.id.register_pwd);
        pwdConfirm = (EditText) findViewById(R.id.register_again);
        codeGet = (TextView) findViewById(R.id.register_codetv);
        codeGet.setOnClickListener(this);
        findViewById(R.id.register_back).setOnClickListener(this);
        findViewById(R.id.register_confirm).setOnClickListener(this);
    }


    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case REGISTER:
                    ToastUtil.showLong("用户注册成功");
                    finish();
                    break;
                case REGISTER_ERROR:
                    DialogNotileUtil.show(RegisterActivity.this, registerError);
                    break;
                case REGISTER_FAIL:
                    DialogNotileUtil.show(RegisterActivity.this, "用户注册失败");
                    break;
                case CODE:
                    ToastUtil.showLong("发送验证码成功");
                    break;
                case CODE_ERROR:
                    codeCheck = true;
                    timeCount.cancel();
                    codeGet.setText("获取验证码");
                    DialogNotileUtil.show(RegisterActivity.this, codeError);
                    break;
                case CODE_FAIL:
                    codeCheck = true;
                    timeCount.cancel();
                    codeGet.setText("获取验证码");
                    DialogNotileUtil.show(RegisterActivity.this, "发送验证码失败");
                    break;
                case RESULT_ERROR:
                    codeCheck = true;
                    timeCount.cancel();
                    codeGet.setText("获取验证码");
                    DialogNotileUtil.show(RegisterActivity.this, "后台接口有误");
                    break;
            }
        }
    };


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_confirm:
                registerCheck();
                break;
            case R.id.register_codetv:
                codeCheck();
                break;
            case R.id.register_back:
                finish();
                break;
        }
    }

    /*注册信息的格式审核，成功后向后台发出请求*/
    private void registerCheck() {
        String nameStr = username.getText().toString();
        String phoneStr = phone.getText().toString();
        String codeString = code.getText().toString();
        String pwdString = pwd.getText().toString();
        String pwdconfirString = pwdConfirm.getText().toString();
        boolean code = TextUtils.isEmpty(codeString);
        boolean pwd = TextUtils.isEmpty(pwdString);
        boolean pwdconfirm = TextUtils.isEmpty(pwdconfirString);
        boolean checkphone = TextUtils.isEmpty(phoneStr);
        boolean name = TextUtils.isEmpty(nameStr);
        if (name) {
            ToastUtil.showLong("用户名不能为空");
            return;
        }
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
        if (pwd) {
            ToastUtil.showLong("密码不能为空");
            return;
        } else if (pwdString.length() != 6) {
            ToastUtil.showLong("密码不是6位");
            return;
        }
        if (pwdconfirm) {
            ToastUtil.showLong("确认密码不能为空");
            return;
        } else if (pwdconfirString.length() != 6) {
            ToastUtil.showLong("确认密码不是6位");
            return;
        } else if (!pwdconfirString.equals(pwdString)) {
            ToastUtil.showLong("密码和确认密码不相同");
            return;
        }
        executor.submit(registerRunnable);
    }

    Runnable registerRunnable = new Runnable() {
        @Override
        public void run() {
            if (new Random().nextInt(2) % 2 == 1) {
                handler.sendEmptyMessage(REGISTER);
            } else {
                handler.sendEmptyMessage(REGISTER_FAIL);
            }
        }
    };


    private void codeCheck() {
        String phoneStr = phone.getText().toString();
        boolean checkphone = TextUtils.isEmpty(phoneStr);
        if (checkphone) {
            ToastUtil.showLong("手机号码不能为空");
            return;
        } else if (phoneStr.length() != 11) {
            ToastUtil.showLong("手机格式不对");
            return;
        }
        if (codeCheck) {
            executor.submit(codeRunnable);
            timeCount.start();
        }
    }

    Runnable codeRunnable = new Runnable() {

        @Override
        public void run() {
            if (new Random().nextInt(2) % 2 == 1) {
                handler.sendEmptyMessage(CODE_FAIL);
            } else {
                handler.sendEmptyMessage(CODE);
            }
        }
    };


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
