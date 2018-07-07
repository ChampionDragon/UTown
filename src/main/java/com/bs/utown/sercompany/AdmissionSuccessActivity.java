package com.bs.utown.sercompany;

import android.os.Bundle;
import android.view.View;

import com.bs.utown.R;
import com.bs.utown.base.BaseActivity;
import com.bs.utown.user.UserApplyActivity;
import com.bs.utown.util.SmallUtil;

/**
 * Description: 提交完成
 * AUTHOR: Champion Dragon
 * created at 2018/7/6
 **/
public class AdmissionSuccessActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admission_success);
        baseapp.TemaddActivity(this);
        findViewById(R.id.admissionsuccess).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SmallUtil.getActivity(AdmissionSuccessActivity.this, UserApplyActivity.class);
                baseapp.TemfinishAllActivity();
            }
        });
    }


}
