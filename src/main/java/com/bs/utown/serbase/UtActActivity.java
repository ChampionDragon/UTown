package com.bs.utown.serbase;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.bs.utown.R;
import com.bs.utown.adapter.ActAdapter;
import com.bs.utown.base.BaseActivity;
import com.bs.utown.bean.ActBean;
import com.bs.utown.constant.Constant;
import com.bs.utown.constant.SpKey;
import com.bs.utown.util.SmallUtil;
import com.bs.utown.util.TimeUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Description: 园区活动
 * AUTHOR: Champion Dragon
 * created at 2018/6/27
 **/
public class UtActActivity extends BaseActivity {
    private String[] url = {Constant.url_1, Constant.url_2, Constant.url_3};
    private String tag = "UtActActivity";
    private ListView lv;
    private List<ActBean> list;
    private ActAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseapp.addActivity(this);
        setContentView(R.layout.activity_ut_act);
        initView();
    }

    private void initView() {
        lv = (ListView) findViewById(R.id.utact_lv);
        findViewById(R.id.back_utact).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                baseapp.finishActivity();
            }
        });
        initData();
        lv.setOnItemClickListener(listener);
    }


    AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            ActBean actBean = list.get(position);
            Bundle bundle = new Bundle();
            bundle.putSerializable(SpKey.actBean, actBean);
            SmallUtil.getActivity(UtActActivity.this, UtActdtlActivity.class, bundle);
        }
    };


    /*初始化数据*/
    private void initData() {
        list = new ArrayList<>();
        int sum = new Random().nextInt(11);
        for (int i = 0; i < sum; i++) {
            ActBean bean = new ActBean();
            String time = TimeUtil.long2time(System.currentTimeMillis() - i * Constant.Day, Constant.formatlogin);
            bean.setName("活动_" + i);
            bean.setPlace("活动地点_" + i);
            bean.setTime(time);
            bean.setUrl(url[new Random().nextInt(3)]);
            list.add(bean);
        }
        initLv(lv, list);
    }

    /*初始化listView*/
    private void initLv(ListView lv, List<ActBean> loginList) {
        adapter = (ActAdapter) lv.getAdapter();
        if (adapter == null) {
            adapter = new ActAdapter(list, this);
            lv.setAdapter(adapter);
        } else {
            adapter.setData(loginList);
            adapter.notifyDataSetChanged();
        }
    }
}
