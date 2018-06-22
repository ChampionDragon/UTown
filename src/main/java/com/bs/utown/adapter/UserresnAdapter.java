package com.bs.utown.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bs.utown.R;
import com.bs.utown.bean.ResnBean;
import com.bs.utown.constant.SpKey;
import com.bs.utown.user.UserResndetailActivity;
import com.bs.utown.util.SmallUtil;
import com.bs.utown.util.ViewHolderUtil;

import java.util.List;

/**
 * Description: 我的预定适配器
 * AUTHOR: Champion Dragon
 * created at 2018/6/19
 **/

public class UserresnAdapter extends BaseAdapter{
    private List<ResnBean> list;
    private Context context;

    public UserresnAdapter(List<ResnBean> list, Context context) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_userresn, null);
        }

        TextView name = ViewHolderUtil.get(convertView, R.id.userresn_name);
        TextView time = ViewHolderUtil.get(convertView, R.id.userresn_time);

        final ResnBean resnBean = list.get(position);
        name.setText(resnBean.getName());
        time.setText(resnBean.getTime());

        ViewHolderUtil.get(convertView,R.id.userresn_detail).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(SpKey.resnBean, resnBean);
                SmallUtil.getActivity((Activity) context, UserResndetailActivity.class, bundle);
            }
        });

        return convertView;
    }


    /**
     * 添加刷新的数据
     */
    public void setData(List<ResnBean> scanResults) {
        list = scanResults;
    }

}
