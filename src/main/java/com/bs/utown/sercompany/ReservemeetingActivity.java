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
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Description: 会议室预定
 * AUTHOR: Champion Dragon
 * created at 2018/6/14
 **/
public class ReservemeetingActivity extends BaseActivity implements View.OnClickListener {
    private String tag = "ReservemeetingActivity";
    private ListView lv;
    private List<ResnBean> list;
    private ResnAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseapp.addActivity(this);
        setContentView(R.layout.activity_resnmeeting);
        initView();
    }

    private void initView() {
        findViewById(R.id.back_resnmeeting).setOnClickListener(this);
        lv = (ListView) findViewById(R.id.resnmeeting_lv);
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
            SmallUtil.getActivity(ReservemeetingActivity.this, ReservedetailActivity.class, bundle);
        }
    };


    /*初始化数据*/
    private void initData() {
//        executor.submit(dataRunnable);

        list = new ArrayList<>();

        OkHttpUtils.get().url(Constant.Utownmeeting).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {
                Logs.w(e + "  " + i);
            }

            @Override
            public void onResponse(String s, int i) {
                try {
                    Logs.v(tag + " " + s);
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
            resnBean.setArea(jsonObject.getString("area") + "㎡");
            resnBean.setNum(jsonObject.getString("contain") + "人");
            resnBean.setName(jsonObject.getString("name"));
            resnBean.setPlace(jsonObject.getString("position"));
            resnBean.setUrl("http://www.bsznyun.com" + jsonObject.getString("thumb"));
            resnBean.setEquipment(jsonObject.getString("equipments"));
            resnBean.setPrice(jsonObject.getString("price"));
            resnBean.setPriceUnit("元/小时");
            resnBean.setNumUnit("可容纳人数：");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return resnBean;
    }


    /*向后台请求数据*/
//    Runnable dataRunnable = new Runnable() {
//        @Override
//        public void run() {
//            String result = HttpByGet.executeHttpGet(Constant.Utownmeeting);
//            Logs.v(result + "    result");
//            JSONObject jb = null;
//            try {
//                jb = new JSONObject(result);
//            } catch (JSONException e) {
//                e.printStackTrace();
//                Logs.e(e.toString());
//            }
//            if (result.equals(HttpByGet.error)) {
//                handler.sendEmptyMessage(ERROR);
//                return;
//            } else if (result.equals("fail")) {
//                handler.sendEmptyMessage(FAIL);
//            } else {
//                handler.sendEmptyMessage(SUCCESS);
//            }
//        }
//    };


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
            case R.id.back_resnmeeting:
                baseapp.finishActivity();
                break;
        }
    }
}
