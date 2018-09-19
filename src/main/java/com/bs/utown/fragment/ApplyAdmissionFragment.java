package com.bs.utown.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.bs.utown.R;
import com.bs.utown.adapter.ApplyAdapter;
import com.bs.utown.bean.ApplyBean;
import com.bs.utown.constant.Constant;
import com.bs.utown.constant.SpKey;
import com.bs.utown.sercompany.AdmissionDetailActivity;
import com.bs.utown.user.UserApplyActivity;
import com.bs.utown.util.HttpByGet;
import com.bs.utown.util.Logs;
import com.bs.utown.util.SmallUtil;
import com.bs.utown.util.TimeUtil;
import com.bs.utown.view.DialogNoticeUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: 入园申请的碎片
 * AUTHOR: Champion Dragon
 * created at 2018/6/11
 **/
public class ApplyAdmissionFragment extends Fragment {
    private View view;
    private String tag = "ApplyAdmissionFragment";
    private ListView lv;
    private List<ApplyBean> list;
    private ApplyAdapter adapter;
    private static final int SUCCESS = 47;
    private static final int FAIL = 48;
    private static final int ERROR = 49;

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
        lv.setOnItemClickListener(listener);
        initData();
    }

    /*初始化数据*/
    private void initData() {
        list = new ArrayList<>();
        final UserApplyActivity activity = (UserApplyActivity) getActivity();
        activity.executor.submit(new Runnable() {
            @Override
            public void run() {
                String url = Constant.ApplyInquire + HttpByGet.get("phone", activity.spUser.getString(SpKey.UserPhone));
                String result = HttpByGet.executeHttpGet(url);
                Logs.i(tag + "  86 " + result);
                if (result.equals(HttpByGet.error)) {
                    handler.sendEmptyMessage(ERROR);
                    return;
                } else if (result.equals(" []")) {
                    handler.sendEmptyMessage(FAIL);
                } else {
                    try {
                        JSONArray array = new JSONArray(result);
                        for (int k = 0; k < array.length(); k++) {
                            JSONObject jsonObject = (JSONObject) array.get(k);
                            ApplyBean bean = parseJson(jsonObject);
                            list.add(bean);
                        }
                        Message msg = new Message();
                        msg.obj = list;
                        msg.what = SUCCESS;
                        handler.sendMessage(msg);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    /*解析JsonObject的数据*/
    private ApplyBean parseJson(JSONObject jsonObject) {
        ApplyBean bean = new ApplyBean();
        bean.setApplyStr("入园申请");
        try {
            bean.setApplyTime(jsonObject.getString("apply_date"));
            bean.setName(jsonObject.getString("company_name"));
            bean.setUser(jsonObject.getString("username"));
            bean.setPhone(jsonObject.getString("phone"));
//            bean.setEmail(jsonObject.getString("email"));
            bean.setDesc(jsonObject.getString("desc"));
            bean.setEnterNum(jsonObject.getString("enter_number"));
            bean.setApplyRs("通过");
//            String str = jsonObject.getString("status");
//            if (str.equals("0")) {
//                bean.setApplyRs("待审核");
//            } else if (str.equals("1")) {
//                bean.setApplyRs("通过");
//            } else {
//                bean.setApplyRs("拒绝");
//            }

        } catch (JSONException e) {
            Logs.e(tag + "136:" + e);
        }
        return bean;
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


    /*每个item监听*/
    AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            ApplyBean applyBean = list.get(position);
            Bundle bundle = new Bundle();
            bundle.putSerializable(SpKey.applyBean, applyBean);
            SmallUtil.getActivity(getActivity(), AdmissionDetailActivity.class, bundle);
        }
    };


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SUCCESS:
                    list = (List<ApplyBean>) msg.obj;
                    initLv(lv, list);
                    break;
                case FAIL:
                    DialogNoticeUtil.show(getActivity(), "未查询到您的入驻申请记录");
                    break;
                case ERROR:
                    DialogNoticeUtil.show(getActivity(), "访问后台失败");
                    break;
            }
        }
    };


}
