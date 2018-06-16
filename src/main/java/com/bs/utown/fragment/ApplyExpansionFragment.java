package com.bs.utown.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.bs.utown.R;
import com.bs.utown.adapter.ApplyAdapter;
import com.bs.utown.bean.ApplyBean;
import com.bs.utown.constant.Constant;
import com.bs.utown.util.Logs;
import com.bs.utown.util.TimeUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Description: 扩租申请的碎片
 * AUTHOR: Champion Dragon
 * created at 2018/6/11
 **/
public class ApplyExpansionFragment extends Fragment {
    private View view;
    private String tag = "ApplyExpansionFragment";
    private ListView lv;
    private String[] result = {"通过", "拒绝"};
    private List<ApplyBean> list;
    private ApplyAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_apply, container, false);
        initView(view);
        Logs.e(tag + " 40  我出现了 " + TimeUtil.long2time(System.currentTimeMillis(),Constant.formatlogin));
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Logs.v(tag + " 40  我消失了  " + TimeUtil.long2time(System.currentTimeMillis(),Constant.formatlogin));
    }


    @Override
    public void onResume() {
        super.onResume();
        Logs.d(tag + " onResume  " + TimeUtil.long2time(System.currentTimeMillis(),Constant.formatlogin));
    }

    private void initView(View view) {
        lv = view.findViewById(R.id.lv_apply);
        initData();
    }

    /*初始化数据*/
    private void initData() {
        list = new ArrayList<>();
        int b = new Random().nextInt(100000000);
        int sum = new Random().nextInt(11);
        for (int i = 0; i < sum; i++) {
            ApplyBean bean = new ApplyBean();
            bean.setApplyRs(result[new Random().nextInt(2)]);
            String time = TimeUtil.long2time(System.currentTimeMillis() - i * b, Constant.formatlogin);
            bean.setApplyTime(time);
            bean.setApplyStr("扩租申请");
            list.add(bean);
        }
        initLv(lv, list);
    }

    /*初始化listView*/
    private void initLv(ListView lv, List<ApplyBean> loginList) {
        adapter = (ApplyAdapter) lv.getAdapter();
        if (adapter == null) {
            adapter = new ApplyAdapter(list, getActivity());
            lv.setAdapter(adapter);
        } else {
            adapter.setData(loginList);
            adapter.notifyDataSetChanged();
        }
    }
}
