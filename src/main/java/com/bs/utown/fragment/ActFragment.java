package com.bs.utown.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.bs.utown.R;
import com.bs.utown.adapter.ActAdapter;
import com.bs.utown.bean.ActBean;
import com.bs.utown.constant.Constant;
import com.bs.utown.util.Logs;
import com.bs.utown.util.TimeUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Description: 已报名的活动
 * AUTHOR: Champion Dragon
 * created at 2018/6/12
 **/
public class ActFragment extends Fragment{
    private View view;
    private String[] url={Constant.url_1,Constant.url_2,Constant.url_3};
    private String tag = "ActFragment";
    private ListView lv;
    private List<ActBean> list;
    private ActAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_apply, container, false);
        initView(view);
        Logs.e(tag + " 40  我出现了 " + TimeUtil.long2time(System.currentTimeMillis(), Constant.formatlogin));
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Logs.v(tag + " 47  我消失了  " + TimeUtil.long2time(System.currentTimeMillis(),Constant.formatlogin));
    }


    @Override
    public void onResume() {
        super.onResume();
        Logs.d(tag+" onResume  " + TimeUtil.long2time(System.currentTimeMillis(),Constant.formatlogin));
    }

    private void initView(View view) {
        lv = view.findViewById(R.id.lv_apply);
        initData();
    }

    /*初始化数据*/
    private void initData() {
        list = new ArrayList<>();
        int b = new Random().nextInt(1000000000);
        int sum = new Random().nextInt(11);
        for (int i = 0; i < sum; i++) {
            ActBean bean = new ActBean();
            String time = TimeUtil.long2time(System.currentTimeMillis() - i * b, Constant.formatlogin);
            bean.setName("活动_"+i);
            bean.setPlace("活动地点_"+i);
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
            adapter = new ActAdapter(list, getActivity());
            lv.setAdapter(adapter);
        } else {
            adapter.setData(loginList);
            adapter.notifyDataSetChanged();
        }
    }
}
