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
import com.bs.utown.constant.Constant;
import com.bs.utown.util.HttpByGet;
import com.bs.utown.util.Logs;
import com.bs.utown.util.ToastUtil;
import com.bs.utown.view.DialogNotileUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Description: 用户注册
 * AUTHOR: Champion Dragon
 * created at 2018/5/29
 **/
public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    private EditText phone, code_et, pwd, pwdConfirm, username;
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
    private String codeResult="";//短信返回的验证码

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
        code_et = (EditText) findViewById(R.id.register_code);
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
                    DialogNotileUtil.show(RegisterActivity.this, codeError + "\n发送验证码失败");
                    break;
                case RESULT_ERROR:
                    codeCheck = true;
                    timeCount.cancel();
                    codeGet.setText("获取验证码");
                    DialogNotileUtil.show(RegisterActivity.this, "访问后台失败");
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
        String codeString = code_et.getText().toString();
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
        }
//        else if (pwdString.length() != 6) {
//            ToastUtil.showLong("密码不是6位");
//            return;
//        }
        if (pwdconfirm) {
            ToastUtil.showLong("确认密码不能为空");
            return;
        }
//        else if (pwdconfirString.length() != 6) {
//            ToastUtil.showLong("确认密码不是6位");
//            return;
//        }
        else if (!pwdconfirString.equals(pwdString)) {
            ToastUtil.showLong("密码和确认密码不相同");
            return;
        }

//        if(!codeResult.equals(code_et.getText().toString())){
//            DialogNotileUtil.show(this,"验证码错误");
//            return;
//        }
        executor.submit(registerRunnable);
    }

    Runnable registerRunnable = new Runnable() {
        @Override
        public void run() {
            String url = Constant.register + HttpByGet.get("username", username.getText().toString(),
                    "phone", phone.getText().toString(), "password", pwd.getText().toString());
            String result = HttpByGet.executeHttpGet(url);
            if (result.equals(HttpByGet.error)) {
                handler.sendEmptyMessage(RESULT_ERROR);
                return;
            }
            try {
                JSONObject jsonObject = new JSONObject(result);
                String ret = jsonObject.getString("result");
                if (ret.equals("ok")) {
                    handler.sendEmptyMessage(REGISTER);
                } else if (ret.equals("fail")) {
                    registerError = jsonObject.getString("info");
                    handler.sendEmptyMessage(REGISTER_ERROR);
                } else {
                    handler.sendEmptyMessage(REGISTER_FAIL);
                }
            } catch (JSONException e) {
                Logs.e("Register199   " + e);
                e.printStackTrace();
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
            String url = Constant.mobileMsg + HttpByGet.get("mobile", phone.getText().toString());
            String executeHttpGet = HttpByGet.executeHttpGet(url);
            if (executeHttpGet.equals(HttpByGet.error)) {
                handler.sendEmptyMessage(RESULT_ERROR);
                return;
            }
            try {
                JSONObject jsonObject = new JSONObject(executeHttpGet);
                int errocode = jsonObject.getInt("errcode");
                if (errocode == 0) {
                    handler.sendEmptyMessage(CODE);
                    codeResult=jsonObject.getString("code");
                    Logs.i("Register237   返回的短信： "+codeResult);
                } else {
                    codeError = jsonObject.getString("ret");
                    handler.sendEmptyMessage(CODE_FAIL);
                }
            } catch (JSONException e) {
                e.printStackTrace();
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
