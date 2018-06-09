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
import com.bs.utown.util.SmallUtil;
import com.bs.utown.util.ToastUtil;
import com.bs.utown.view.DialogNotileUtil;

import org.json.JSONException;
import org.json.JSONObject;

public class ResetActivity extends BaseActivity implements View.OnClickListener {
    private EditText phone, code;
    private TextView codeGet;
    private TimeCount timeCount;
    private boolean codeCheck = true;
    private static final int CODE = 3;
    private static final int CODE_ERROR = 4;
    private static final int CODE_FAIL = 5;
    private static final int RESULT_ERROR = 6;
    private String codeError;
    private String codeResult = "";//短信返回的验证码
    private String tag="ResetActivity";


    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case CODE:
                    ToastUtil.showLong("发送验证码成功");
                    break;
                case CODE_ERROR:
                    codeCheck = true;
                    timeCount.cancel();
                    codeGet.setText("获取验证码");
                    DialogNotileUtil.show(ResetActivity.this, codeError);
                    break;
                case CODE_FAIL:
                    codeCheck = true;
                    timeCount.cancel();
                    codeGet.setText("获取验证码");
                    DialogNotileUtil.show(ResetActivity.this, "发送验证码失败");
                    break;
                case RESULT_ERROR:
                    codeCheck = true;
                    timeCount.cancel();
                    codeGet.setText("获取验证码");
                    DialogNotileUtil.show(ResetActivity.this, "访问后台失败");
                    break;
            }
        }
    };


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
                resetCheck();
                break;
            case R.id.reset_codetv:
                codeCheck();
                break;
        }
    }


    /*验证格式*/
    private void resetCheck() {
        String phoneStr = phone.getText().toString();
        String codeString = code.getText().toString();
        boolean codeBoolean = TextUtils.isEmpty(codeString);
        boolean checkphone = TextUtils.isEmpty(phoneStr);
        if (checkphone) {
            ToastUtil.showLong("手机号码不能为空");
            return;
        } else if (phoneStr.length() != 11) {
            ToastUtil.showLong("手机不是11位");
            return;
        }
        if (codeBoolean) {
            ToastUtil.showLong("验证码不能为空");
            return;
        } else if (codeString.length() != 6) {
            ToastUtil.showLong("验证码不是6位");
            return;
        }

        if (!codeResult.equals(code.getText().toString())) {
            DialogNotileUtil.show(this, "验证码错误");
        } else {
            Bundle bundle=new Bundle();
            bundle.putString("phone",phoneStr);
            SmallUtil.getActivity(ResetActivity.this, PwdActivity.class,bundle);
        }
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
                    codeResult = jsonObject.getString("code");
                    Logs.i(tag+"168  返回的短信： " + codeResult);
                } else {
                    codeError = jsonObject.getString("ret");
                    handler.sendEmptyMessage(CODE_ERROR);
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
