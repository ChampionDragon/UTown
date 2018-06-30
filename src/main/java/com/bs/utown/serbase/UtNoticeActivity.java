package com.bs.utown.serbase;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.bs.utown.R;
import com.bs.utown.adapter.NoticeAdapter;
import com.bs.utown.base.BaseActivity;
import com.bs.utown.bean.NoticeBean;
import com.bs.utown.constant.Constant;
import com.bs.utown.util.TimeUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Description: 园区公告
 * AUTHOR: Champion Dragon
 * created at 2018/6/27
 **/
public class UtNoticeActivity extends BaseActivity {
    private String tag = "UtActActivity";
    private ListView lv;
    private List<NoticeBean> list;
    private NoticeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseapp.addActivity(this);
        setContentView(R.layout.activity_ut_notice);
        initView();
    }

    private void initView() {
        lv = (ListView) findViewById(R.id.utnotice_lv);
        findViewById(R.id.back_utnotice).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                baseapp.finishActivity();
            }
        });
        initData();
    }


    /*初始化数据*/
    private void initData() {
        list = new ArrayList<>();
        int sum = new Random().nextInt(11);
        for (int i = 0; i < sum; i++) {
            NoticeBean bean = new NoticeBean();
            String time = TimeUtil.long2time(System.currentTimeMillis() - i * Constant.Day, Constant.formatlogin);
            bean.setMsg("公告_" + i);
            bean.setTime(time);
            list.add(bean);
        }
        initLv(lv, list);
    }

    /*初始化listView*/
    private void initLv(ListView lv, List<NoticeBean> loginList) {
        adapter = (NoticeAdapter) lv.getAdapter();
        if (adapter == null) {
            adapter = new NoticeAdapter(list, this);
            lv.setAdapter(adapter);
        } else {
            adapter.setData(loginList);
            adapter.notifyDataSetChanged();
        }
    }
}
