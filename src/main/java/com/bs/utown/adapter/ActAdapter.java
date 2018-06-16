package com.bs.utown.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bs.utown.R;
import com.bs.utown.bean.ActBean;
import com.bs.utown.util.ViewHolderUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Description: 活动的适配器
 * AUTHOR: Champion Dragon
 * created at 2018/6/12
 **/

public class ActAdapter extends BaseAdapter {
    private List<ActBean> list;
    private Context context;

    public ActAdapter(List<ActBean> list, Context context) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_act, null);
        }

        TextView time = ViewHolderUtil.get(convertView, R.id.act_time);
        TextView place = ViewHolderUtil.get(convertView, R.id.act_place);
        TextView name = ViewHolderUtil.get(convertView, R.id.act_name);
        ImageView iv = ViewHolderUtil.get(convertView, R.id.act_iv);

        ActBean actBean = list.get(position);
        time.setText(actBean.getTime());
        place.setText(actBean.getPlace());
        name.setText(actBean.getName());
        Picasso.with(context).load(actBean.getUrl()).placeholder(R.mipmap.logo).error(R.mipmap.logo).into(iv);

        return convertView;
    }


    /**
     * 添加刷新的数据
     */
    public void setData(List<ActBean> scanResults) {
        list = scanResults;
    }

}
