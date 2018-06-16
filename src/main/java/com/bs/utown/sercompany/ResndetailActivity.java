package com.bs.utown.sercompany;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bs.utown.R;
import com.bs.utown.base.BaseActivity;
import com.bs.utown.bean.ResnBean;
import com.bs.utown.constant.SpKey;
import com.bs.utown.pickerview.other.pickerViewUtil;
import com.bs.utown.util.Logs;
import com.bs.utown.util.SmallUtil;
import com.bs.utown.util.ToastUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.bs.utown.constant.SpKey.resnBean;

public class ResndetailActivity extends BaseActivity implements View.OnClickListener {
    private TextView name, equipment, place, price, time, notice;
    private ImageView iv;
    private String tag = "ResndetailActivity";
    public static final int Resntime = 5;
    private CheckBox msg, app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseapp.TemaddActivity(this);
        setContentView(R.layout.activity_resndetail);
        initView();
        initData();
    }

    private void initView() {
        name = (TextView) findViewById(R.id.resndetail_name);
        equipment = (TextView) findViewById(R.id.resndetail_equipment);
        place = (TextView) findViewById(R.id.resndetail_place);
        price = (TextView) findViewById(R.id.resndetail_price);
        iv = (ImageView) findViewById(R.id.resndetail_iv);
        time = (TextView) findViewById(R.id.resndetail_time_tv);
        findViewById(R.id.back_resndetail).setOnClickListener(this);
        findViewById(R.id.resndetail_time).setOnClickListener(this);
        findViewById(R.id.resndetail_resn).setOnClickListener(this);
        findViewById(R.id.resndetail_notice).setOnClickListener(this);
        notice = (TextView) findViewById(R.id.resndetail_notice_tv);
        msg = (CheckBox) findViewById(R.id.resndetail_msg);
        app = (CheckBox) findViewById(R.id.resndetail_app);
    }

    private void initData() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            ResnBean bean = (ResnBean) extras.getSerializable(resnBean);
            name.setText(bean.getName());
            equipment.setText(bean.getEquipment());
            place.setText(bean.getPlace());
            price.setText(bean.getPrice());
            Picasso.with(this).load(bean.getUrl()).placeholder(R.mipmap.logo).error(R.mipmap.logo).into(iv);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_resndetail:
                baseapp.TemfinishActivity(this);
                break;
            case R.id.resndetail_time:
                startActivityForResult(new Intent(ResndetailActivity.this, ResntimeActivity.class), 0);
                break;
            case R.id.resndetail_resn:
                if (msg.isChecked()) {
                    if (app.isChecked()) {
                        ToastUtil.showLong("通知方式：应用内+短信");
                    } else {
                        ToastUtil.showLong("通知方式：短信");
                    }
                } else {
                    if (app.isChecked()) {
                        ToastUtil.showLong("通知方式：应用内");
                    }
                }
                SmallUtil.getActivity(ResndetailActivity.this, ResnsuccessActivity.class);
                break;
            case R.id.resndetail_notice:
                notice();
                break;
        }
    }

    /*通知时间*/
    private void notice() {
        final ArrayList<String> list = new ArrayList<>();
        for (int i = 1; i < 5; i++) {
            list.add("会议前" + 15 * i + "分钟");
        }
        pickerViewUtil.alertBottomWheelOption(this, "请选择通知时间", list, new pickerViewUtil.OnWheelViewClick() {
            @Override
            public void onClick(View view, int postion) {
                notice.setText(list.get(postion));
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Logs.d(requestCode + " " + resultCode + " " + data);
        switch (resultCode) {
            case Resntime:
                time.setText(data.getStringExtra(SpKey.resnTime));
                break;
        }
    }
}
