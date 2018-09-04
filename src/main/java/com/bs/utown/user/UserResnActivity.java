package com.bs.utown.user;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.bs.utown.R;
import com.bs.utown.adapter.UserresnAdapter;
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
 * Description:会议室的预定
 * AUTHOR: Champion Dragon
 * created at 2018/6/20
 **/
public class UserResnActivity extends BaseActivity implements View.OnClickListener {
    private String tag = "UserResnActivity";
    private ListView lv;
    private List<ResnBean> list;
    private UserresnAdapter adapter;
    private String openId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseapp.addActivity(this);
        setContentView(R.layout.activity_user_resn);
        initView();
    }

    private void initView() {
        findViewById(R.id.back_userresn).setOnClickListener(this);
        lv = (ListView) findViewById(R.id.userresn_lv);
        initData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_userresn:
                baseapp.finishActivity();
                break;
        }
    }


    /*初始化数据*/
    private void initData() {
          /*由于openID是唯一值,所以我们将用户手机号作为openId*/
        openId = baseapp.sp.getString(SpKey.UserPhone);
        list = new ArrayList<>();
        String url = Constant.Ordermeeting + "?openId=" + openId;
        Logs.i("url:\n" + url);
        OkHttpUtils.get().url(url).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {
                Logs.w(e + tag + "74  " + i);
                DialogNoticeUtil.show(UserResnActivity.this, "访问接口失败");
            }

            @Override
            public void onResponse(String s, int i) {
                try {
                    Logs.v(tag + "81\n" + s);
                    if (s.equals(" []")) {
                        DialogNoticeUtil.show(UserResnActivity.this, "未查询到用户会议室的订单");
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


    /*解析JsonObject的数据*/
    private ResnBean parseJson(JSONObject jsonObject) {
        ResnBean resnBean = new ResnBean();
        try {
//            resnBean.setArea(jsonObject.getString("area") + "㎡");
//            resnBean.setNum(jsonObject.getString("contain") + "人");
            resnBean.setName(jsonObject.getString("name"));
//            resnBean.setPlace(jsonObject.getString("position"));
            resnBean.setUrl("http://www.bsznyun.com" + jsonObject.getString("thumb"));
//            resnBean.setEquipment(jsonObject.getString("equipments"));
            resnBean.setPrice(jsonObject.getString("fee"));
            resnBean.setPriceUnit("元/小时");
            resnBean.setNumUnit("可容纳人数：");
            resnBean.setId(jsonObject.getString("cid"));
            resnBean.setOrderTime(jsonObject.getString("order_date"));
            resnBean.setReserveTime(jsonObject.getString("date") + "  " + jsonObject.getString("containid"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return resnBean;
    }


    /*初始化listView*/
    private void initLv(ListView lv, List<ResnBean> loginList) {
        adapter = (UserresnAdapter) lv.getAdapter();
        if (adapter == null) {
            adapter = new UserresnAdapter(list, this);
            lv.setAdapter(adapter);
        } else {
            adapter.setData(loginList);
            adapter.notifyDataSetChanged();
        }
    }


}
