package com.bs.utown.account;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.bs.utown.R;
import com.bs.utown.base.BaseActivity;
import com.bs.utown.constant.Constant;
import com.bs.utown.util.HttpByGet;
import com.bs.utown.util.ToastUtil;
import com.bs.utown.view.DialogNoticeUtil;

import org.json.JSONException;
import org.json.JSONObject;

public class PwdActivity extends BaseActivity implements View.OnClickListener {
    private EditText pwd, pwdConfirm;
    private static final int RESETPWD = 0;
    private static final int RESETPWD_FAIL = 2;
    private static final int RESULT_ERROR = 6;
    private String phone;//传来的电话号码

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseapp.TemaddActivity(this);
        setContentView(R.layout.activity_pwd);
        phone = getIntent().getExtras().getString("phone");
        initView();
    }

    private void initView() {
        findViewById(R.id.pwd_back).setOnClickListener(this);
        findViewById(R.id.pwd_confirm).setOnClickListener(this);
        pwd = (EditText) findViewById(R.id.pwd_pwd);
        pwdConfirm = (EditText) findViewById(R.id.pwd_again);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pwd_back:
                baseapp.TemfinishActivity(this);
                break;
            case R.id.pwd_confirm:
                confirmCheck();
                break;
        }
    }

    private void confirmCheck() {
        String pwdString = pwd.getText().toString();
        String pwdconfirString = pwdConfirm.getText().toString();
        boolean pwd = TextUtils.isEmpty(pwdString);
        boolean pwdconfirm = TextUtils.isEmpty(pwdconfirString);

        if (pwd) {
            ToastUtil.showLong("新密码不能为空");
            return;
        }
//        else if (pwdString.length() != 6) {
//            ToastUtil.showLong("新密码不是6位");
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
        executor.submit(pwdRunnable);
    }


    Runnable pwdRunnable = new Runnable() {
        @Override
        public void run() {
            String url = Constant.reset + HttpByGet.get("phone", phone, "password", pwd.getText().toString());
            String result = HttpByGet.executeHttpGet(url);
            if (result.equals(HttpByGet.error)) {
                handler.sendEmptyMessage(RESULT_ERROR);
                return;
            }
            try {
                JSONObject jsonObject = new JSONObject(result);
                String resultStr = jsonObject.getString("result");
                if (resultStr.equals("ok")) {
                    handler.sendEmptyMessage(RESETPWD);
                } else {
                    handler.sendEmptyMessage(RESETPWD_FAIL);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case RESETPWD:
                    ToastUtil.showLong("修改密码成功");
                    baseapp.TemfinishAllActivity();
                    break;
                case RESETPWD_FAIL:
                    DialogNoticeUtil.show(PwdActivity.this, "修改密码失败");
                    break;
                case RESULT_ERROR:
                    DialogNoticeUtil.show(PwdActivity.this, "访问后台失败");
                    break;
            }
        }
    };


}
