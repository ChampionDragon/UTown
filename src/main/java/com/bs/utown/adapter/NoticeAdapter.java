package com.bs.utown.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bs.utown.R;
import com.bs.utown.bean.NoticeBean;
import com.bs.utown.util.ViewHolderUtil;

import java.util.List;

/**
 * Description: 公告的适配器
 * AUTHOR: Champion Dragon
 * created at 2018/6/27
 **/

public class NoticeAdapter extends BaseAdapter {
    private List<NoticeBean> list;
    private Context context;

    public NoticeAdapter(List<NoticeBean> list, Context context) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_utnotice, null);
        }

        TextView time = ViewHolderUtil.get(convertView, R.id.utnotice_time);
        TextView msg = ViewHolderUtil.get(convertView, R.id.utnotice_str);

        NoticeBean noticeBean = list.get(position);
        time.setText(noticeBean.getTime());
        msg.setText(noticeBean.getMsg());

        return convertView;
    }


    /**
     * 添加刷新的数据
     */
    public void setData(List<NoticeBean> scanResults) {
        list = scanResults;
    }

}
