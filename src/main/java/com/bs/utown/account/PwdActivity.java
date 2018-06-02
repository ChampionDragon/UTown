package com.bs.utown.account;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.bs.utown.R;
import com.bs.utown.base.BaseActivity;
import com.bs.utown.util.ToastUtil;

public class PwdActivity extends BaseActivity implements View.OnClickListener {
    private EditText pwd, pwdConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseapp.TemaddActivity(this);
        setContentView(R.layout.activity_pwd);
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
        } else if (pwdString.length() != 6) {
            ToastUtil.showLong("新密码不是6位");
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

        baseapp.TemfinishAllActivity();

    }
}
