package com.bs.utown.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bs.utown.R;
import com.bs.utown.bean.ResnBean;
import com.bs.utown.util.ViewHolderUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Description: 预定的适配器
 * AUTHOR: Champion Dragon
 * created at 2018/6/14
 **/

public class ResnAdapter extends BaseAdapter {
    private List<ResnBean> list;
    private Context context;

    public ResnAdapter(List<ResnBean> list, Context context) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_resn, null);
        }

        TextView money = ViewHolderUtil.get(convertView, R.id.resn_price);
        TextView place = ViewHolderUtil.get(convertView, R.id.resn_place);
        TextView name = ViewHolderUtil.get(convertView, R.id.resn_name);
        ImageView iv = ViewHolderUtil.get(convertView, R.id.resn_iv);
        TextView equipment = ViewHolderUtil.get(convertView, R.id.resn_equipment);

        ResnBean resnBean = list.get(position);
        money.setText(resnBean.getPrice() + resnBean.getPriceUnit());
        place.setText(resnBean.getPlace());
        name.setText(resnBean.getName());
        equipment.setText(resnBean.getEquipment());
        Picasso.with(context).load(resnBean.getUrl()).placeholder(R.mipmap.logo).error(R.mipmap.logo).into(iv);

        return convertView;
    }


    /**
     * 添加刷新的数据
     */
    public void setData(List<ResnBean> scanResults) {
        list = scanResults;
    }

}
