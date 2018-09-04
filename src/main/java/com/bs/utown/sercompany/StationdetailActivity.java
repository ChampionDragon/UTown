package com.bs.utown.sercompany;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bs.utown.R;
import com.bs.utown.base.BaseActivity;
import com.bs.utown.bean.ResnBean;
import com.bs.utown.bean.UserInfo;
import com.bs.utown.constant.Constant;
import com.bs.utown.constant.SpKey;
import com.bs.utown.util.HttpByGet;
import com.bs.utown.util.Logs;
import com.bs.utown.util.ObjectSave;
import com.bs.utown.util.ToastUtil;
import com.bs.utown.view.DialogNoticeUtil;
import com.squareup.picasso.Picasso;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;

import static com.bs.utown.constant.SpKey.resnBean;

/**
 * Description: 工位详情
 * AUTHOR: Champion Dragon
 * created at 2018/8/24
 **/
public class StationdetailActivity extends BaseActivity implements View.OnClickListener {
    private TextView name, place, num, numUnit, remark, monthfee, monthdeposit, monthtotal,
            monthremark, yearfee, yeardeposit, yeartotal, yearremark;
    private ImageView iv;
    private String tag = "StationdetailActivity";
    private int monthSum, yearSum;
    private CheckBox checkMonth, checkYear;
    private String userName, openId, officeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stationdetail);
        baseapp.TemaddActivity(this);
        initView();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            ResnBean bean = (ResnBean) extras.getSerializable(resnBean);
            officeId = bean.getId();
            initData(officeId);
        }
        UserInfo userInfo = ObjectSave.getUserInfo();
        userName = userInfo.getName();
       /*由于openID是唯一值,所以我们将用户手机号作为openId*/
        openId = baseapp.sp.getString(SpKey.UserPhone);
    }

    private void initData(String id) {
        OkHttpUtils.get().url(Constant.Utownofficedetail + id).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {
                Logs.e(tag + " 50 " + e + "  " + i);
                DialogNoticeUtil.show(StationdetailActivity.this, "访问接口失败");
            }

            @Override
            public void onResponse(String s, int i) {
                try {
                    JSONObject jsonObject = (JSONObject) new JSONArray(s).get(0);
                    parseJson(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Logs.e(tag + " 66\n" + e);
                }
            }
        });
    }


    /**
     * 解析JSon数据
     */
    private void parseJson(JSONObject jsonObject) {
        Logs.v(tag + " 76: " + jsonObject);
        try {
            name.setText(jsonObject.getString("office_name"));
            place.setText(jsonObject.getString("region"));
            num.setText(jsonObject.getString("number"));
            /*0:可容纳人数 1:剩余工位*/
            if (jsonObject.getString("number_unit").equals("0")) {
                numUnit.setText("可容纳人数：");
            } else {
                numUnit.setText("剩余工位：");
            }
            Picasso.with(this).load("http://www.bsznyun.com" + jsonObject.getString("thumb"))
                    .placeholder(R.mipmap.logo).error(R.mipmap.logo).into(iv);

            remark.setText(jsonObject.getString("remark"));
            monthfee.setText(jsonObject.getString("month_fee"));
            monthdeposit.setText(jsonObject.getString("month_deposit"));
            monthSum = Integer.valueOf(jsonObject.getString("month_fee")) + Integer.valueOf(jsonObject.getString("month_deposit"));
            monthtotal.setText(monthSum + "");

            yearfee.setText(jsonObject.getString("year_fee"));
            yeardeposit.setText(jsonObject.getString("year_deposit"));
            yearSum = Integer.valueOf(jsonObject.getString("year_fee")) + Integer.valueOf(jsonObject.getString("year_deposit"));
            yeartotal.setText(yearSum + "");

            yearremark.setText(jsonObject.getString("year_remark"));
            monthremark.setText(jsonObject.getString("month_remark"));
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private void initView() {
        findViewById(R.id.back_stationdetail).setOnClickListener(this);
        findViewById(R.id.stationtail_pay).setOnClickListener(this);

        iv = (ImageView) findViewById(R.id.stationdetail_iv);
        name = (TextView) findViewById(R.id.stationtail_name);//工位名
        numUnit = (TextView) findViewById(R.id.stationtail_numunit);//人数单位
        num = (TextView) findViewById(R.id.stationtail_num);//人数
        place = (TextView) findViewById(R.id.stationtail_place);//地址
        remark = (TextView) findViewById(R.id.stationtail_remark);//备注说明
        monthfee = (TextView) findViewById(R.id.stationtail_monthfee);//月租费用
        monthdeposit = (TextView) findViewById(R.id.stationtail_monthdeposit);//月租押金
        monthtotal = (TextView) findViewById(R.id.stationtail_monthtotal);//月租总金额
        monthremark = (TextView) findViewById(R.id.stationtail_monthremark);//月租说明
        yearfee = (TextView) findViewById(R.id.stationtail_yearfee);//年租费用
        yeardeposit = (TextView) findViewById(R.id.stationtail_yeardeposit);//年租押金
        yeartotal = (TextView) findViewById(R.id.stationtail_yeartotal);//年租总金额
        yearremark = (TextView) findViewById(R.id.stationtail_yearremark);//年租说明

        checkMonth = (CheckBox) findViewById(R.id.stationtail_checkmonth);
        checkYear = (CheckBox) findViewById(R.id.stationtail_checkyear);
        checkMonth.setChecked(true);
        checkMonth.setOnClickListener(this);
        checkYear.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_stationdetail:
                baseapp.TemfinishActivity();
                break;
            case R.id.stationtail_pay:
                Pay();
                break;
            case R.id.stationtail_checkmonth:
                if (checkYear.isChecked()) {
                    checkYear.setChecked(false);
                }
                checkMonth.setChecked(true);
                break;
            case R.id.stationtail_checkyear:
                if (checkMonth.isChecked()) {
                    checkMonth.setChecked(false);
                }
                checkYear.setChecked(true);
                break;
        }
    }

    /**
     * 付款的操作
     */
    private void Pay() {
        if (checkMonth.isChecked()) {
            PushBackGround(monthSum + "");
        } else if (checkYear.isChecked()) {
            PushBackGround(yearSum + "");
        }
    }

    private void PushBackGround(String fee) {
        String url = Constant.Apply + HttpByGet.get("g", "Api", "m", "Wechat", "a", "officeBook", "office_id", officeId,
                "username", userName, "openId", openId, "fee", fee);
        OkHttpUtils.get().url(url).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {
                Logs.e(tag + " 192 " + e + "\n" + i);
            }

            @Override
            public void onResponse(String s, int i) {
                try {
                    JSONObject js = new JSONObject(s);
                    if (js.getString("result").equals("ok")) {
                        ToastUtil.showLong("预定成功 ");
                        baseapp.TemfinishAllActivity();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


}
