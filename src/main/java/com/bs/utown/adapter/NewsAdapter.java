package com.bs.utown.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bs.utown.R;
import com.bs.utown.bean.NewsBean;
import com.bs.utown.util.ViewHolderUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Description:新闻的适配器
 * AUTHOR: Champion Dragon
 * created at 2018/6/26
 **/

public class NewsAdapter extends BaseAdapter {
    private List<NewsBean> list;
    private Context context;

    public NewsAdapter(List<NewsBean> list, Context context) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_utnews, null);
        }

        TextView time = ViewHolderUtil.get(convertView, R.id.utnews_time);
        TextView name = ViewHolderUtil.get(convertView, R.id.utnews_str);
        ImageView iv = ViewHolderUtil.get(convertView, R.id.utnews_iv);

        NewsBean newsBean = list.get(position);
        time.setText(newsBean.getTime());
        name.setText(newsBean.getName());
        Picasso.with(context).load(newsBean.getUrl()).placeholder(R.mipmap.logo).error(R.mipmap.logo).into(iv);

        return convertView;
    }


    /**
     * 添加刷新的数据
     */
    public void setData(List<NewsBean> scanResults) {
        list = scanResults;
    }

}

