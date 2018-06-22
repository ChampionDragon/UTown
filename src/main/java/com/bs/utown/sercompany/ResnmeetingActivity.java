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
import com.bs.utown.util.SmallUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Description: 会议室预定
 * AUTHOR: Champion Dragon
 * created at 2018/6/14
 **/
public class ResnmeetingActivity extends BaseActivity implements View.OnClickListener {
    private String[] url = {Constant.url_1, Constant.url_2, Constant.url_3};
    private String[] money = {"11", "18", "27"};
    private String[] equipment = {"空调", "电脑", "投影仪"};
    private String tag = "ResnmeetingActivity";
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
            SmallUtil.getActivity(ResnmeetingActivity.this, ResndetailActivity.class, bundle);
        }
    };


    /*初始化数据*/
    private void initData() {
        list = new ArrayList<>();
        int sum = new Random().nextInt(11);
        for (int i = 0; i < sum; i++) {
            ResnBean resnBean = new ResnBean();
            String moneyStr = getString(R.string.price, money[new Random().nextInt(3)]);
            resnBean.setPrice(moneyStr);
            resnBean.setArea("XX平方米");
            resnBean.setNum("YY人");
            resnBean.setName("会议室_" + i);
            resnBean.setPlace(i + "栋X楼YYY");
            resnBean.setUrl(url[new Random().nextInt(3)]);
            resnBean.setEquipment(equipment[new Random().nextInt(3)]);
            list.add(resnBean);
        }
        initLv(lv, list);
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
            case R.id.back_resnmeeting:
                baseapp.finishActivity();
                break;
        }
    }


}
