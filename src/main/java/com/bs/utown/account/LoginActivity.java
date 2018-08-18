package com.bs.utown.account;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.bs.utown.R;
import com.bs.utown.UtActivity;
import com.bs.utown.base.BaseActivity;
import com.bs.utown.base.BaseApplication;
import com.bs.utown.constant.Constant;
import com.bs.utown.constant.SpKey;
import com.bs.utown.util.HttpByGet;
import com.bs.utown.util.Logs;
import com.bs.utown.util.NetConnectUtil;
import com.bs.utown.util.SmallUtil;
import com.bs.utown.util.ToastUtil;
import com.bs.utown.view.DialogNoticeUtil;
import com.bs.utown.wxapi.WXEntryActivity;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

/**
 * Description: 登录类
 * AUTHOR: Champion Dragon
 * created at 2018/5/28
 **/
public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private String tag = "LoginActivity";
    private static final int LOGIN = 0;
    private static final int LOGIN_FAIL = 1;
    private static final int RESULT_ERROR = 2;
    private EditText phone, psw;
    private String wx_access_token, wx_openid;//微信登录返回得到openid，access_token;


    /*创建用于接收数据的广播*/
    private BroadcastReceiver wxReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(WXEntryActivity.ACTION_GETWX)) {
                wx_access_token = intent.getStringExtra(WXEntryActivity.access_token);
                wx_openid = intent.getStringExtra(WXEntryActivity.openid);
                getWxUserInfo();
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /*注册微信广播*/
        IntentFilter filter = new IntentFilter(WXEntryActivity.ACTION_GETWX);
        LocalBroadcastManager.getInstance(this).registerReceiver(wxReceiver, filter);

        boolean netConnect = NetConnectUtil.NetConnect(this);
        if (!netConnect) {
            DialogNoticeUtil.show(this, "请先将网络打开");
        }

        Test();

        initView();
    }

    /*测试*/
    private void Test() {
    }

    private void initView() {
        phone = (EditText) findViewById(R.id.login_phone);
        psw = (EditText) findViewById(R.id.login_pwd);
        findViewById(R.id.login_login).setOnClickListener(this);
        findViewById(R.id.login_register).setOnClickListener(this);
        findViewById(R.id.login_resetpwd).setOnClickListener(this);
        findViewById(R.id.login_weixin).setOnClickListener(this);
//        findViewById(R.id.login_guide).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_login:
                loginCheck();
//                SmallUtil.getActivity(LoginActivity.this, UtActivity.class);
                break;
            case R.id.login_resetpwd:
                SmallUtil.getActivity(LoginActivity.this, ResetActivity.class);
                break;
            case R.id.login_register:
                SmallUtil.getActivity(LoginActivity.this, RegisterActivity.class);
                break;
            case R.id.login_weixin:
                wxLogin();
                break;
//            case R.id.login_guide:
//                SmallUtil.getActivity(LoginActivity.this, MainActivity.class);
//            spUser.putBoolean(SpKey.isLogin, true);
//            spUser.putString(SpKey.UserName, phone.getText().toString());//系统保留用户名
//            managerDb.addOrUpdateLogin(phone.getText().toString(), TimeUtil.long2time(System.currentTimeMillis(), Constant.formatlogin));
//                finish();
//                break;
        }
    }

    /*微信登录*/
    private void wxLogin() {
        //通过WXAPIFactory工厂获取IWXApI的示例
        IWXAPI api = WXAPIFactory.createWXAPI(this, Constant.APP_ID_WX, true);
        //将应用的appid注册到微信
        api.registerApp(Constant.APP_ID_WX);
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
//                req.scope = "snsapi_login";//提示scope参数错误或者没有scope权;限用snsapi_base提示没权限
        req.state = "wechat_sdk_微信登录";
        //用于保持请求和回调的状态，授权请求后原样带回给第三方。
        // 该参数可用于防止csrf攻击（跨站请求伪造攻击），建议第三方带上该参数，可设置为简单的随机数加session进行校验。
        api.sendReq(req);
    }


    /*登录的验证*/
    private void loginCheck() {
        String phoneStr = phone.getText().toString();
        boolean checkphone = TextUtils.isEmpty(phoneStr);
        String pswStr = psw.getText().toString();
        boolean checkpsw = TextUtils.isEmpty(pswStr);
        if (checkphone) {
            ToastUtil.showLong("手机号码不能为空");
            return;
        } else if (phoneStr.length() != 11) {
            ToastUtil.showLong("手机格式不对");
            return;
        } else if (checkpsw) {
            ToastUtil.showLong("密码不能为空");
            return;
        }
        executor.submit(loginRunnable);
    }

    Runnable loginRunnable = new Runnable() {
        @Override
        public void run() {
            String url = Constant.Login + HttpByGet.get("user", phone.getText().toString(), "passwd", psw.getText().toString());
            String result = HttpByGet.executeHttpGet(url);
            if (result.equals(HttpByGet.error)) {
                handler.sendEmptyMessage(RESULT_ERROR);
                return;
            } else if (result.equals("fail")) {
                handler.sendEmptyMessage(LOGIN_FAIL);
            } else if (result.equals("ok")) {
                handler.sendEmptyMessage(LOGIN);
            }
        }
    };

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case LOGIN:
                    SmallUtil.getActivity(LoginActivity.this, UtActivity.class);
                    spUser.putBoolean(SpKey.isLogin, true);
                    spUser.putString(SpKey.UserPhone, phone.getText().toString());//系统保留用户的手机号
//            managerDb.addOrUpdateLogin(phone.getText().toString(), TimeUtil.long2time(System.currentTimeMillis(), Constant.formatlogin));
                    finish();
                    break;
                case LOGIN_FAIL:
                    DialogNoticeUtil.show(LoginActivity.this, "登录失败\n" + "密码错误或账号不存在");
                    break;
                case RESULT_ERROR:
                    DialogNoticeUtil.show(LoginActivity.this, "访问后台失败");
                    break;
            }
        }
    };


    /*获取WX的用户信息*/
    private void getWxUserInfo() {
        //https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID
        OkHttpUtils.get()
                .url("https://api.weixin.qq.com/sns/userinfo")
                .addParams("access_token", wx_access_token)
                .addParams("openid", wx_openid)//openid:授权用户唯一标识
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(okhttp3.Call call, Exception e, int id) {
                        Logs.e(tag + "190获取错误 " + e + "  " + id + "  " + call);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Logs.v(tag + "195 个人信息 " + response + "  " + id);
                        DialogNoticeUtil.show(LoginActivity.this, response);
                    }
                });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(wxReceiver);
    }

    @Override
    public void onBackPressed() {
        BaseApplication.getInstance().exitApp();
    }
}
