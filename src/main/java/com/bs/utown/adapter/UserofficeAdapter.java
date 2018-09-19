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
 * Description: 共享办公预定适配器
 * AUTHOR: Champion Dragon
 * created at 2018/9/14
 **/
public class UserofficeAdapter extends BaseAdapter {
    private List<ResnBean> list;
    private Context context;

    public UserofficeAdapter(List<ResnBean> list, Context context) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_useroffice, null);
        }

        TextView fee = ViewHolderUtil.get(convertView, R.id.officeadapter_fee);
        TextView name = ViewHolderUtil.get(convertView, R.id.officeadapter_name);
        ImageView iv = ViewHolderUtil.get(convertView, R.id.officeadapter_iv);
        TextView date = ViewHolderUtil.get(convertView, R.id.officeadapter_date);

        ResnBean resnBean = list.get(position);
        fee.setText(resnBean.getPrice());
        name.setText(resnBean.getName());
        date.setText(resnBean.getReserveTime());
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
