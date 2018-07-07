package com.bs.utown.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bs.utown.R;
import com.bs.utown.adapter.ImageViewAdapter;
import com.bs.utown.listener.DiadisListener;

import java.util.List;

/**
 * Description: ViewPager的对话框
 * AUTHOR: Champion Dragon
 * created at 2018/7/2
 **/

public class DialogVp {
    private Context context;
    private Dialog dialog;
    private ViewPager vp;

    public DialogVp(Context context, List<ImageView> list, final DiadisListener listener) {
        this.context = context;
        dialog = new Dialog(context, R.style.dialog);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_vp, null);
        vp = view.findViewById(R.id.vp_vp);
        final int[] flag = {99};
        for (int i = 0; i < list.size(); i++) {
            final int finalI = i;
            list.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    flag[0] = finalI;
                    listener.dismiss(flag[0]);
                }
            });
        }
        vp.setAdapter(new ImageViewAdapter(list));


        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        dialog.setContentView(view, new LinearLayout.LayoutParams(
                dm.widthPixels * 5 / 6,//LinearLayout.LayoutParams.MATCH_PARENT
                dm.heightPixels * 4 / 5));
        dialog.show();

        dialog.setCancelable(true);//点击空白处令弹框消失
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (flag[0] == 99) {
                    listener.dismiss(flag[0]);
                }
            }
        });
    }


    public void closeDia() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }


}
