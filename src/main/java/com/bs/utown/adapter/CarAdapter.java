package com.bs.utown.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bs.utown.R;
import com.bs.utown.bean.CarBean;
import com.bs.utown.util.ViewHolderUtil;

import java.util.List;

/**
 * Description: 车辆管理适配器
 * AUTHOR: Champion Dragon
 * created at 2018/6/22
 **/

public class CarAdapter extends BaseAdapter {
    private List<CarBean> list;
    private Context context;

    public CarAdapter(List<CarBean> list, Context context) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_car, null);
        }

        TextView name = ViewHolderUtil.get(convertView, R.id.car_name);
        TextView num = ViewHolderUtil.get(convertView, R.id.car_num);
        TextView color = ViewHolderUtil.get(convertView, R.id.car_color);

        CarBean carBean = list.get(position);
        name.setText(carBean.getName());
        num.setText(carBean.getPlateNum());
        color.setText(carBean.getColor());


        return convertView;
    }


    /**
     * 添加刷新的数据
     */
    public void setData(List<CarBean> scanResults) {
        list = scanResults;
    }

}

