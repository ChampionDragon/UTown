package com.bs.utown.user;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.bs.utown.R;
import com.bs.utown.adapter.UserofficeAdapter;
import com.bs.utown.base.BaseActivity;
import com.bs.utown.bean.ResnBean;
import com.bs.utown.constant.Constant;
import com.bs.utown.constant.SpKey;
import com.bs.utown.util.Logs;
import com.bs.utown.view.DialogNoticeUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Description:办公室预定记录
 * AUTHOR: Champion Dragon
 * created at 2018/9/13
 **/
public class UserOfficeActivity extends BaseActivity implements View.OnClickListener {
    private String tag = "UserOfficeActivity";
    private ListView lv;
    private List<ResnBean> list;
    private UserofficeAdapter adapter;
    private String openId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_office);
        baseapp.addActivity(this);
        initView();
    }

    private void initView() {
        findViewById(R.id.back_useroffice).setOnClickListener(this);
        lv = (ListView) findViewById(R.id.useroffice_lv);
        initData();
    }

    private void initData() {
        openId = baseapp.sp.getString(SpKey.UserPhone);
        list = new ArrayList<>();
        String url = Constant.Orderoffice + "?openId=" + openId;
        Logs.i("url:\n" + url);
        OkHttpUtils.get().url(url).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {
                Logs.w(e + tag + "61  " + i);
                DialogNoticeUtil.show(UserOfficeActivity.this, "访问接口失败");
            }

            @Override
            public void onResponse(String s, int i) {
                try {
                    Logs.v(tag + "68\n" + s);
                    if (s.equals(" []")) {
                        DialogNoticeUtil.show(UserOfficeActivity.this, "未查询到用户共享办公的订单");
                        return;
                    }
                    JSONArray jsonArray = new JSONArray(s);
                    for (int k = 0; k < jsonArray.length(); k++) {
                        JSONObject jsonObject = (JSONObject) jsonArray.get(k);
                        ResnBean resnBean = parseJson(jsonObject);
                        list.add(resnBean);
                        initLv(lv, list);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Logs.e(e.toString());
                }
            }
        });
    }


    private ResnBean parseJson(JSONObject jsonObject) {
        ResnBean resnBean = new ResnBean();
        try {
            resnBean.setName(jsonObject.getString("office_name"));
            resnBean.setUrl("http://www.bsznyun.com" + jsonObject.getString("thumb"));
            resnBean.setPrice("金额：" + jsonObject.getString("fee") + "元");
            resnBean.setReserveTime("预定日期：" + jsonObject.getString("order_date"));
            Logs.w("1111111111111111");
        } catch (JSONException e) {
            Logs.e(tag + "99:" + e);
        }
        return resnBean;
    }


    /*初始化listView*/
    private void initLv(ListView lv, List<ResnBean> loginList) {
        adapter = (UserofficeAdapter) lv.getAdapter();
        if (adapter == null) {
            adapter = new UserofficeAdapter(list, this);
            lv.setAdapter(adapter);
        } else {
            adapter.setData(loginList);
            adapter.notifyDataSetChanged();
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_useroffice:
                baseapp.finishActivity();
                break;
        }
    }


}
