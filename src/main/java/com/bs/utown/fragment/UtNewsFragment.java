package com.bs.utown.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.bs.utown.R;
import com.bs.utown.adapter.NewsAdapter;
import com.bs.utown.bean.NewsBean;
import com.bs.utown.constant.Constant;
import com.bs.utown.util.Logs;
import com.bs.utown.util.TimeUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Description: 园区资讯的碎片
 * AUTHOR: Champion Dragon
 * created at 2018/6/26
 **/
public class UtNewsFragment extends Fragment {
    private View view;
    private String[] url = {Constant.url_1, Constant.url_2, Constant.url_3};
    private String tag = "ActFragment";
    private ListView lv;
    private List<NewsBean> list;
    private NewsAdapter adapter;

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
        Logs.v(tag + " 47  我消失了  " + TimeUtil.long2time(System.currentTimeMillis(), Constant.formatlogin));
    }


    @Override
    public void onResume() {
        super.onResume();
        Logs.d(tag + " onResume  " + TimeUtil.long2time(System.currentTimeMillis(), Constant.formatlogin));
    }

    private void initView(View view) {
        lv = view.findViewById(R.id.lv_apply);
        initData();
    }

    /*初始化数据*/
    private void initData() {
        list = new ArrayList<>();
        int sum = new Random().nextInt(11);
        for (int i = 0; i < sum; i++) {
            NewsBean bean = new NewsBean();
            String time = TimeUtil.long2time(System.currentTimeMillis() - i * Constant.Day, Constant.formatlogin);
            bean.setName("活动_" + i);
            bean.setTime(time);
            bean.setUrl(url[new Random().nextInt(3)]);
            list.add(bean);
        }
        initLv(lv, list);
    }

    /*初始化listView*/
    private void initLv(ListView lv, List<NewsBean> loginList) {
        adapter = (NewsAdapter) lv.getAdapter();
        if (adapter == null) {
            adapter = new NewsAdapter(list, getActivity());
            lv.setAdapter(adapter);
        } else {
            adapter.setData(loginList);
            adapter.notifyDataSetChanged();
        }
    }


}
