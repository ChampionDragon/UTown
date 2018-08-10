package com.bs.utown.sercompany;

import android.os.Bundle;
import android.view.View;

import com.bs.utown.R;
import com.bs.utown.base.BaseActivity;

/**
 * Description: 条款及条件
 * AUTHOR: Champion Dragon
 * created at 2018/8/7
 **/
public class TermsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);
        findViewById(R.id.back_terms).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
