package com.bs.utown.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bs.utown.R;
import com.bs.utown.bean.ApplyBean;
import com.bs.utown.util.ViewHolderUtil;

import java.util.List;

/**
 * Description: 申请的适配器
 * AUTHOR: Champion Dragon
 * created at 2018/6/11
 **/
public class ApplyAdapter extends BaseAdapter {
    private List<ApplyBean> list;
    private Context context;

    public ApplyAdapter(List<ApplyBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_apply, null);
        }

        TextView time = ViewHolderUtil.get(convertView, R.id.apply_time);
        TextView reslut = ViewHolderUtil.get(convertView, R.id.apply_result);
        TextView str = ViewHolderUtil.get(convertView, R.id.apply_str);

        ApplyBean applyBean = list.get(position);
        time.setText(applyBean.getApplyTime());
        reslut.setText(applyBean.getApplyRs());
        str.setText(applyBean.getApplyStr());

        return convertView;
    }


    /**
     * 添加刷新的数据
     */
    public void setData(List<ApplyBean> scanResults) {
        list = scanResults;
    }


}
