package com.bs.utown.sercompany;

import android.os.Bundle;
import android.view.View;

import com.bs.utown.R;
import com.bs.utown.base.BaseActivity;
import com.bs.utown.user.UserOfficeActivity;
import com.bs.utown.user.UserResnActivity;
import com.bs.utown.util.SmallUtil;

/**
 * Description: 预定成功
 * AUTHOR: Champion Dragon
 * created at 2018/6/15
 **/
public class ReservesuccessActivity extends BaseActivity {
    public static String classname = "classname";
    private String activity = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseapp.TemaddActivity(this);
        setContentView(R.layout.activity_resnsuccess);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            activity = extras.getString(classname);
        }
        findViewById(R.id.resnsuccess_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                baseapp.TemfinishAllActivity();
            }
        });
        findViewById(R.id.resnsuccess_resn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                baseapp.TemfinishAllActivity();
                if (activity.equals("ReservedetailActivity")) {
                    SmallUtil.getActivity(ReservesuccessActivity.this, UserResnActivity.class);
                } else if (activity.equals("StationdetailActivity")) {
                    SmallUtil.getActivity(ReservesuccessActivity.this, UserOfficeActivity.class);
                }
            }
        });
    }
}
