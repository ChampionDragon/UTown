package com.bs.utown.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bs.utown.R;
import com.bs.utown.adapter.CarAdapter;
import com.bs.utown.base.BaseActivity;
import com.bs.utown.bean.CarBean;
import com.bs.utown.constant.SpKey;
import com.bs.utown.util.Logs;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: 车辆管理
 * AUTHOR: Champion Dragon
 * created at 2018/6/21
 **/
public class UserCarmgrActivity extends BaseActivity implements View.OnClickListener {
    private TextView add;
    private ListView lv;
    private LinearLayout ll;
    private List<CarBean> list;
    private CarAdapter adapter;
    public static final int Addcar = 66;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseapp.addActivity(this);
        setContentView(R.layout.activity_user_carmgr);
        initView();
        list = new ArrayList<>();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (list.size() > 0) {
            ll.setVisibility(View.INVISIBLE);
            add.setVisibility(View.VISIBLE);
            initLv(lv, list);
        } else {
            add.setVisibility(View.INVISIBLE);
            ll.setVisibility(View.VISIBLE);
        }
    }


    /*初始化数据*/
//    private void initData() {
//        list = new ArrayList<>();
//        int sum = new Random().nextInt(5);
//        for (int i = 0; i < sum; i++) {
//            CarBean carBean = new CarBean();
//            carBean.setColor(color[new Random().nextInt(7)]);
//            carBean.setName(name[new Random().nextInt(3)]);
//            carBean.setPlateNum(num[new Random().nextInt(3)]);
//            list.add(carBean);
//        }
//        initLv(lv, list);
//    }

    /*初始化listView*/
    private void initLv(ListView lv, List<CarBean> loginList) {
        adapter = (CarAdapter) lv.getAdapter();
        if (adapter == null) {
            adapter = new CarAdapter(list, this);
            lv.setAdapter(adapter);
        } else {
            adapter.setData(loginList);
            adapter.notifyDataSetChanged();
        }
    }


    private void initView() {
        findViewById(R.id.back_usercarmgr).setOnClickListener(this);
        add = (TextView) findViewById(R.id.usercarmgr_add);
        add.setOnClickListener(this);
        lv = (ListView) findViewById(R.id.usercarmgr_lv);
        ll = (LinearLayout) findViewById(R.id.usercarmgr_ll);
        findViewById(R.id.usercarmgr_add_btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_usercarmgr:
                baseapp.finishActivity();
                break;
            case R.id.usercarmgr_add:
                startActivityForResult(new Intent(UserCarmgrActivity.this, UserCaraddActivity.class), 0);
                break;
            case R.id.usercarmgr_add_btn:
                startActivityForResult(new Intent(UserCarmgrActivity.this, UserCaraddActivity.class), 0);
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Logs.d(requestCode + " " + resultCode + " " + data);
        switch (resultCode) {
            case Addcar:
                CarBean carBean = (CarBean) data.getSerializableExtra(SpKey.carAdd);
                list.add(carBean);
                break;
        }
    }


}
