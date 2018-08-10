package com.bs.utown.account;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.bs.utown.R;
import com.bs.utown.UtActivity;
import com.bs.utown.base.BaseActivity;
import com.bs.utown.constant.SpKey;
import com.bs.utown.util.SmallUtil;
import com.bs.utown.util.SystemUtil;

public class WelcomeActivity extends BaseActivity {

    public int DELAYTIME = 933;
    public static final int MAIN = 0;
    public static final int LOGIN = 1;
    public static final int GUIDE = 2;
    boolean isFisrt;
    boolean isLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_welcome);
        super.onCreate(savedInstanceState);
        isFisrt = spUser.getBoolean(SpKey.isFirst, true);
        int curVer = SystemUtil.VersionCode();
        int preVer = spUser.getInt(SpKey.preVer);
        isLogin = spUser.getBoolean(SpKey.isLogin);
        //isFisrt || curVer > preVer     !isLogin
        if (isFisrt || curVer > preVer) {
            handler.sendEmptyMessageDelayed(GUIDE, DELAYTIME);
            spUser.putInt(SpKey.preVer, curVer);
            spUser.putBoolean(SpKey.isFirst, false);
        } else if (!isLogin) {
            handler.sendEmptyMessageDelayed(LOGIN, DELAYTIME);
        } else {
            handler.sendEmptyMessageDelayed(MAIN, DELAYTIME);
        }

    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MAIN:
                    SmallUtil.getActivity(WelcomeActivity.this, UtActivity.class);
                    finish();
                    break;
                case LOGIN:
                    SmallUtil.getActivity(WelcomeActivity.this, LoginActivity.class);
                    finish();
                    break;
                case GUIDE:
                    SmallUtil.getActivity(WelcomeActivity.this, LoginActivity.class);
                    finish();
                    break;
            }
        }
    };
}