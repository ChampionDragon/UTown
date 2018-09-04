package com.bs.utown.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bs.utown.R;
import com.bs.utown.base.BaseActivity;
import com.bs.utown.bean.ResnBean;
import com.bs.utown.constant.Constant;
import com.bs.utown.pickerview.other.pickerViewUtil;
import com.bs.utown.sercompany.ReservetimeActivity;
import com.bs.utown.util.Logs;
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
 * Description:会议室预定详情
 * AUTHOR: Champion Dragon
 * created at 2018/6/20
 **/
public class UserResndetailActivity extends BaseActivity implements View.OnClickListener {

    private TextView name, equipment, place, price, time, notice, area, num;
    private ImageView iv, modify;
    private String tag = "UserResndetailActivity";
    public static final int Resntime = 5;
    private CheckBox msg, app;
    private Button confirm;
    private boolean isModify;
    private String day, begin, over;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseapp.addActivity(this);
        setContentView(R.layout.activity_user_resndetail);
        initView();
        initData();
    }

    private void initView() {
        num = (TextView) findViewById(R.id.userresndetail_num);
        area = (TextView) findViewById(R.id.userresndetail_area);
        name = (TextView) findViewById(R.id.userresndetail_name);
        equipment = (TextView) findViewById(R.id.userresndetail_equipment);
        place = (TextView) findViewById(R.id.userresndetail_place);
        price = (TextView) findViewById(R.id.userresndetail_price);
        iv = (ImageView) findViewById(R.id.userresndetail_iv);
        time = (TextView) findViewById(R.id.userresndetail_time_tv);
        findViewById(R.id.back_userresndetail).setOnClickListener(this);
        findViewById(R.id.userresndetail_time).setOnClickListener(this);
        confirm = (Button) findViewById(R.id.userresndetail_confirm);
        confirm.setOnClickListener(this);
        modify = (ImageView) findViewById(R.id.userresndetail_modify);
        modify.setOnClickListener(this);
        findViewById(R.id.userresndetail_notice).setOnClickListener(this);
        notice = (TextView) findViewById(R.id.userresndetail_notice_tv);
        msg = (CheckBox) findViewById(R.id.userresndetail_msg);
        app = (CheckBox) findViewById(R.id.userresndetail_app);
    }

    private void initData() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            ResnBean bean = (ResnBean) extras.getSerializable(resnBean);
            name.setText(bean.getName());
            price.setText(bean.getPrice() + bean.getPriceUnit());
            time.setText(bean.getReserveTime());
            Picasso.with(this).load(bean.getUrl()).placeholder(R.mipmap.logo).error(R.mipmap.logo).into(iv);
            String cid = bean.getId();//获取订单ID
            String url = Constant.Ordermeetingdetail + cid;
            OkHttpUtils.get().url(url).build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int i) {
                    Logs.e(tag + " 92 " + e + "\n" + i);
                    DialogNoticeUtil.show(UserResndetailActivity.this, "访问接口失败");
                }

                @Override
                public void onResponse(String s, int i) {
                    Logs.d(tag + "102\n" + s);
                    try {
                        JSONObject jsonObject = new JSONObject(s);
                        equipment.setText(jsonObject.getString("equipments"));
                        place.setText(jsonObject.getString("position"));
                        num.setText(jsonObject.getString("contain") + "人");
                        area.setText(jsonObject.getString("area") + "㎡");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });


        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_userresndetail:
                baseapp.finishActivity();
                break;
            case R.id.userresndetail_modify:
                Logs.d(tag + " 91 " + isModify);
                confirm.setVisibility(View.VISIBLE);
                modify.setVisibility(View.GONE);
                isModify = true;
                break;
            case R.id.userresndetail_time:
                if (isModify) {
                    startActivityForResult(new Intent(UserResndetailActivity.this, ReservetimeActivity.class), 0);
                } else {
                    modifyNotce();
                }
                break;
            case R.id.userresndetail_confirm:
                setnotice();
                break;
            case R.id.userresndetail_notice:
                if (isModify) {
                    noticetime();
                } else {
                    modifyNotce();
                }
                break;
        }
    }

    /*通知时间*/
    private void noticetime() {
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

    /*通知方式*/
    private void setnotice() {
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
        baseapp.finishActivity();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Logs.d(requestCode + " " + resultCode + " " + data);
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


    private void modifyNotce() {
        ToastUtil.showShort("请先按屏幕右上方图标“修改键”\n再进行此操作");
    }


}
