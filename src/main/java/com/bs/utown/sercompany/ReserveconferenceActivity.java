package com.bs.utown.sercompany;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.bs.utown.R;
import com.bs.utown.adapter.ResnAdapter;
import com.bs.utown.base.BaseActivity;
import com.bs.utown.bean.ResnBean;
import com.bs.utown.constant.Constant;
import com.bs.utown.constant.SpKey;
import com.bs.utown.util.Logs;
import com.bs.utown.util.SmallUtil;
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
 * Description: 洽谈室预定
 * AUTHOR: Champion Dragon
 * created at 2018/8/14
 **/
public class ReserveconferenceActivity extends BaseActivity implements View.OnClickListener {

    private String tag = "ReserveconferenceActivity";
    private ListView lv;
    private List<ResnBean> list;
    private ResnAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseapp.addActivity(this);
        setContentView(R.layout.activity_resnconference);
        initView();
    }

    private void initView() {
        findViewById(R.id.back_reserveconference).setOnClickListener(this);
        lv = (ListView) findViewById(R.id.resnconference_lv);
        lv.setOnItemClickListener(listener);
        initData();
    }

    /*每个item监听*/
    AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            ResnBean resnBean = list.get(position);
            Bundle bundle = new Bundle();
            bundle.putSerializable(SpKey.resnBean, resnBean);
            SmallUtil.getActivity(ReserveconferenceActivity.this, ReservedetailActivity.class, bundle);
        }
    };


    /*初始化数据*/
    private void initData() {
        list = new ArrayList<>();
        OkHttpUtils.get().url(Constant.Utownoffice).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {
                Logs.w(tag + " 74 " + e + "  " + i);
                DialogNoticeUtil.show(ReserveconferenceActivity.this, "访问接口失败");
            }

            @Override
            public void onResponse(String s, int i) {
                try {
                    Logs.v(tag + " 79 " + s);
                    JSONArray jsonArray = new JSONArray(s);
                    for (int k = 0; k < jsonArray.length(); k++) {
                        JSONObject jsonObject = (JSONObject) jsonArray.get(k);
                        ResnBean resnBean = parseJson(jsonObject);
                        list.add(resnBean);
                        initLv(lv, list);
                    }

                } catch (JSONException e) {
                    Logs.e(tag + " 89 " + e);
                    DialogNoticeUtil.show(ReserveconferenceActivity.this, "解析数据失败");
                }
            }
        });
    }

    /*解析JsonObject的数据*/
    private ResnBean parseJson(JSONObject jsonObject) {
        ResnBean resnBean = new ResnBean();
        try {
            resnBean.setArea(null);
            resnBean.setNum(jsonObject.getString("number") + "人");
            resnBean.setName(jsonObject.getString("office_name"));
            resnBean.setPlace(jsonObject.getString("region"));
            resnBean.setUrl("http://www.bsznyun.com" + jsonObject.getString("thumb"));
            resnBean.setEquipment(jsonObject.getString("ment"));
            resnBean.setPrice(jsonObject.getString("normal_charge"));
           /* 0:元/月   1:元/年*/
            if (jsonObject.getString("price_unit").equals("1")) {
                resnBean.setPriceUnit("元/年");
            } else {
                resnBean.setPriceUnit("元/月");
            }
            /*0:可容纳人数 1:剩余工位*/
            if (jsonObject.getString("price_unit").equals("1")) {
                resnBean.setNumUnit("剩余工位：");
            } else {
                resnBean.setNumUnit("可容纳人数：");
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Logs.e(tag + " 110 " + e);
        }
        return resnBean;
    }


    /*初始化listView*/
    private void initLv(ListView lv, List<ResnBean> loginList) {
        adapter = (ResnAdapter) lv.getAdapter();
        if (adapter == null) {
            adapter = new ResnAdapter(list, this);
            lv.setAdapter(adapter);
        } else {
            adapter.setData(loginList);
            adapter.notifyDataSetChanged();
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_reserveconference:
                baseapp.finishActivity();
                break;
        }
    }
}
