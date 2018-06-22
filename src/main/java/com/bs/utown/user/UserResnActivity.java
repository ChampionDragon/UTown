package com.bs.utown.user;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.bs.utown.R;
import com.bs.utown.adapter.UserresnAdapter;
import com.bs.utown.base.BaseActivity;
import com.bs.utown.bean.ResnBean;
import com.bs.utown.constant.Constant;
import com.bs.utown.util.TimeUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Description:我的预定详情
 * AUTHOR: Champion Dragon
 * created at 2018/6/20
 **/
public class UserResnActivity extends BaseActivity implements View.OnClickListener {
    private String[] url = {Constant.url_1, Constant.url_2, Constant.url_3};
    private String[] money = {"11", "18", "27"};
    private String[] equipment = {"空调", "电脑", "投影仪"};
    private String tag = "UserResnActivity";
    private ListView lv;
    private List<ResnBean> list;
    private UserresnAdapter adapter;

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
        list = new ArrayList<>();
        int sum = new Random().nextInt(11);
        for (int i = 0; i < sum; i++) {
            ResnBean resnBean = new ResnBean();
            String moneyStr = getString(R.string.price, money[new Random().nextInt(3)]);
            resnBean.setPrice(moneyStr);
            resnBean.setName("会议室_" + i);
            resnBean.setTime(TimeUtil.long2time(System.currentTimeMillis() - i * Constant.Day, Constant.formatlogin));
            resnBean.setPlace(i + "栋X楼YYY");
            resnBean.setUrl(url[new Random().nextInt(3)]);
            resnBean.setEquipment(equipment[new Random().nextInt(3)]);
            list.add(resnBean);
        }
        initLv(lv, list);
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
