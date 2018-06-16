package com.bs.utown.sercompany;

import android.os.Bundle;
import android.view.View;

import com.bs.utown.R;
import com.bs.utown.base.BaseActivity;

/**
 * Description: 预定成功
 * AUTHOR: Champion Dragon
 * created at 2018/6/15
 **/
public class ResnsuccessActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseapp.TemaddActivity(this);
        setContentView(R.layout.activity_resnsuccess);
        findViewById(R.id.resnsuccess_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                baseapp.TemfinishAllActivity();
            }
        });




    }
}
