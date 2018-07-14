package com.bs.utown.sercompany;

import android.os.Bundle;
import android.view.View;

import com.bs.utown.R;
import com.bs.utown.base.BaseActivity;
import com.bs.utown.user.UserApplyActivity;
import com.bs.utown.util.SmallUtil;

/**
 * Description: 扩租的提交完成
 * AUTHOR: Champion Dragon
 * created at 2018/7/13
 **/
public class ExpansionSuccessActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expansion_success);
        baseapp.TemaddActivity(this);
        findViewById(R.id.expansionsuccess).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt(UserApplyActivity.expansion, 1);
                SmallUtil.getActivity(ExpansionSuccessActivity.this, UserApplyActivity.class, bundle);
                baseapp.TemfinishAllActivity();
            }
        });
    }
}
