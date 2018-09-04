package com.bs.utown.sercompany;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bs.utown.R;
import com.bs.utown.base.BaseActivity;
import com.bs.utown.bean.ResnBean;
import com.bs.utown.bean.UserInfo;
import com.bs.utown.constant.Constant;
import com.bs.utown.constant.SpKey;
import com.bs.utown.pickerview.other.pickerViewUtil;
import com.bs.utown.util.HttpByGet;
import com.bs.utown.util.Logs;
import com.bs.utown.util.ObjectSave;
import com.bs.utown.util.SmallUtil;
import com.bs.utown.util.ToastUtil;
import com.bs.utown.view.DialogNoticeUtil;
import com.squareup.picasso.Picasso;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.Call;

import static com.bs.utown.constant.SpKey.resnBean;

/**
 * Description: 预定详情
 * AUTHOR: Champion Dragon
 * created at 2018/6/20
 **/
public class ReservedetailActivity extends BaseActivity implements View.OnClickListener {
    private TextView name, equipment, place, price, time, notice, area, num, numUnit;
    private ImageView iv;
    private String tag = "ReservedetailActivity";
    public static final int Resntime = 5;
    private CheckBox msg, app;
    private LinearLayout areaLinearlayout;
    private String day, begin, over, userName, openId, meetingId, fee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseapp.TemaddActivity(this);
        setContentView(R.layout.activity_resndetail);
        initView();
        initData();
    }

    private void initView() {
        areaLinearlayout = (LinearLayout) findViewById(R.id.resndetail_areal);
        area = (TextView) findViewById(R.id.resndetail_area);
        num = (TextView) findViewById(R.id.resndetail_num);
        numUnit = (TextView) findViewById(R.id.resndetail_numunit);
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
        UserInfo userInfo = ObjectSave.getUserInfo();
        userName = userInfo.getName();
       /*由于openID是唯一值,所以我们将用户手机号作为openId*/
        openId = baseapp.sp.getString(SpKey.UserPhone);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            ResnBean bean = (ResnBean) extras.getSerializable(resnBean);
            meetingId = bean.getId();
            name.setText(bean.getName());
            equipment.setText(bean.getEquipment());
            place.setText(bean.getPlace());
            fee = bean.getPrice();
            price.setText(bean.getPrice() + bean.getPriceUnit());
            num.setText(bean.getNum());
            numUnit.setText(bean.getNumUnit());
            if (bean.getArea() != null) {
                area.setText(bean.getArea());
            } else {
                areaLinearlayout.setVisibility(View.GONE);
            }
            Picasso.with(this).load(bean.getUrl()).placeholder(R.mipmap.logo).error(R.mipmap.logo).into(iv);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_resndetail:
                baseapp.TemfinishActivity();
                break;
            case R.id.resndetail_time:
                startActivityForResult(new Intent(ReservedetailActivity.this, ReservetimeActivity.class), 0);
                break;
            case R.id.resndetail_resn:
//                if (msg.isChecked()) {
//                    if (app.isChecked()) {
//                        ToastUtil.showLong("通知方式：应用内+短信");
//                    } else {
//                        ToastUtil.showLong("通知方式：短信");
//                    }
//                } else {
//                    if (app.isChecked()) {
//                        ToastUtil.showLong("通知方式：应用内");
//                    }
//                }
                Reserve();
                break;
            case R.id.resndetail_notice:
                notice();
                break;
        }
    }

    /*预定*/
    private void Reserve() {
        String url = Constant.Apply + HttpByGet.get("g", "Api", "m", "Wechat", "a", "book", "date", day, "begin", begin,
                "over", over, "meeting_id", meetingId, "username", userName, "openId", openId, "fee", fee);
        OkHttpUtils.get().url(url).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {
                Logs.e(tag + " 136 " + e + "\n" + i);
            }

            @Override
            public void onResponse(String s, int i) {
                Logs.v(tag + "141: " + s);
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (jsonObject.getString("result").equals("ok")) {
                        ToastUtil.showLong("预定成功");
                        SmallUtil.getActivity(ReservedetailActivity.this, ReservesuccessActivity.class);
                    } else if (jsonObject.getString("result").equals("false")) {
//                        handler.obtainMessage().sendToTarget();
                        DialogNoticeUtil.show(ReservedetailActivity.this, jsonObject.getString("info"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

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
        Logs.d(tag + "185" + requestCode + " " + resultCode + " " + data);
        switch (resultCode) {
            case Resntime:
                Bundle extras = data.getExtras();
                day = extras.getString("day");
                begin = extras.getString("start");
                over = extras.getString("end");
                time.setText(day + "   " + begin + "--" + over);
                break;
        }
    }


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            DialogNoticeUtil.show(ReservedetailActivity.this, "预定失败");
        }
    };


}
