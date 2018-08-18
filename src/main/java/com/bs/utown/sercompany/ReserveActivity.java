package com.bs.utown.sercompany;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bs.utown.R;
import com.bs.utown.base.BaseActivity;
import com.bs.utown.constant.Constant;
import com.bs.utown.util.SmallUtil;
import com.squareup.picasso.Picasso;

/**
 * Description: 场地预定
 * AUTHOR: Champion Dragon
 * created at 2018/6/13
 **/
public class ReserveActivity extends BaseActivity implements View.OnClickListener {

    private ImageView meeting, conference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseapp.addActivity(this);
        setContentView(R.layout.activity_resnsite);
        initView();
    }

    private void initView() {
        findViewById(R.id.back_resnsite).setOnClickListener(this);
        meeting = (ImageView) findViewById(R.id.resnsite_meeting);
        meeting.setOnClickListener(this);
        conference = (ImageView) findViewById(R.id.resnsite_conference);
        conference.setOnClickListener(this);
        Picasso.with(this).load(Constant.url_1).placeholder(R.mipmap.logo).error(R.mipmap.logo).into(meeting);
        Picasso.with(this).load(Constant.url_2).placeholder(R.mipmap.logo).error(R.mipmap.logo).into(conference);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_resnsite:
                baseapp.finishActivity();
                break;
            case R.id.resnsite_meeting:
                SmallUtil.getActivity(ReserveActivity.this,ReservemeetingActivity.class);
                break;
            case R.id.resnsite_conference:
                SmallUtil.getActivity(ReserveActivity.this,ReserveconferenceActivity.class);
                break;
        }
    }
}
