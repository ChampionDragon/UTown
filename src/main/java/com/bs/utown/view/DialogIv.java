package com.bs.utown.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bs.utown.R;
import com.bs.utown.listener.DiadisListener;

/**
 * Description: 图片资源的对话框
 * AUTHOR: Champion Dragon
 * created at 2018/6/30
 **/

public class DialogIv {
    private Context context;
    private Dialog dialog;
    private ImageView iV;


    public DialogIv(Context context, int resource, final DiadisListener listener) {
        this.context = context;
        dialog = new Dialog(context, R.style.dialog);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_iv, null);
        iV = view.findViewById(R.id.iv_iv);
        iV.setImageResource(resource);
        DisplayMetrics dm = context.getResources().getDisplayMetrics();

        dialog.setContentView(view, new LinearLayout.LayoutParams(
                dm.widthPixels * 5/6,//LinearLayout.LayoutParams.MATCH_PARENT
                dm.heightPixels * 4/5));
        dialog.show();

        dialog.setCancelable(true);//点击空白处令弹框消失
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                listener.dismiss();
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
