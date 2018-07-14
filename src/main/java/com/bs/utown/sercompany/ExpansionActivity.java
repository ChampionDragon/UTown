package com.bs.utown.sercompany;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bs.utown.R;
import com.bs.utown.base.BaseActivity;
import com.bs.utown.constant.Constant;
import com.bs.utown.user.UserApplyActivity;
import com.bs.utown.util.SmallUtil;
import com.squareup.picasso.Picasso;

public class ExpansionActivity extends BaseActivity implements View.OnClickListener {
    private ImageView apply, past;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expansion);
        baseapp.addActivity(this);
        initView();
    }

    private void initView() {
        findViewById(R.id.back_expansion).setOnClickListener(this);
        apply = (ImageView) findViewById(R.id.expansion_apply);
        apply.setOnClickListener(this);
        past = (ImageView) findViewById(R.id.expansion_past);
        past.setOnClickListener(this);
        Picasso.with(this).load(Constant.url_admission).placeholder(R.mipmap.logo).error(R.mipmap.logo).into(apply);
        Picasso.with(this).load(Constant.url_1).placeholder(R.mipmap.logo).error(R.mipmap.logo).into(past);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_expansion:
                baseapp.finishActivity();
                break;
            case R.id.expansion_apply:
                SmallUtil.getActivity(ExpansionActivity.this, ExpansionApplyActivity.class);
                break;
            case R.id.expansion_past:
                Bundle bundle = new Bundle();
                bundle.putInt(UserApplyActivity.expansion, 1);
                SmallUtil.getActivity(ExpansionActivity.this, UserApplyActivity.class, bundle);
                break;
        }
    }
}
