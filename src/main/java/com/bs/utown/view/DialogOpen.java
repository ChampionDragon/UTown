package com.bs.utown.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bs.utown.R;
import com.bs.utown.listener.DiadisListener;
import com.bs.utown.util.CreateCodeUtil;
import com.bs.utown.util.DPUtil;
import com.bs.utown.util.Logs;

/**
 * Description: 开门的弹框
 * AUTHOR: Champion Dragon
 * created at 2018/6/1
 **/

public class DialogOpen {
    private TextView tvLarge, tvSmall;
    private ImageView codeIv;
    private Context context;
    private Dialog dialog;

    public DialogOpen(Context context, DiadisListener listener) {
        this(context, null, null, listener);
    }

    public DialogOpen(Context context, String code, DiadisListener listener) {
        this(context, code, null, listener);
    }

    /**
     * @param code 输出的二维码
     * @param tv   二维码底下的文字
     */
    public DialogOpen(Context context, String code, String tv, final DiadisListener listener) {
        this.context = context;
        dialog = new Dialog(context, R.style.dialog);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_open, null);
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        tvSmall = view.findViewById(R.id.open_tvs);
        tvLarge = view.findViewById(R.id.open_tvl);
        codeIv = view.findViewById(R.id.open_iv);

        if (code == null) {
            codeIv.setImageBitmap(getBitamap("欢迎来到优唐智慧园"));
        } else {
            codeIv.setImageBitmap(getBitamap(code));
        }

        view.findViewById(R.id.open_iv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.dismiss();
            }
        });
        dialog.setCancelable(false);
        dialog.setContentView(view, new LinearLayout.LayoutParams(
                dm.widthPixels * 4 / 5,//LinearLayout.LayoutParams.MATCH_PARENT
                dm.heightPixels * 3 / 5));
        Logs.v(dm.widthPixels + "   " + dm.heightPixels);
        dialog.show();
    }

    /*生成二维码的bitmap*/
    private Bitmap getBitamap(String str) {
        Bitmap qrCodeBitmap = CreateCodeUtil.createQRCode(str, DPUtil.dip2px(context, 300),
                DPUtil.dip2px(context, 300), null);
        return qrCodeBitmap;

    }

    /*更新二维码*/
    public void updateCode(String code) {
        codeIv.setImageBitmap(getBitamap(code));
//        codeIv.invalidate();
        if (!dialog.isShowing() && dialog != null) {
            dialog.show();
        }
    }

    /*更新小字的数据*/
    public void updateSmall(String tv) {
        tvSmall.setText(tv);
//        codeTv.invalidate();
        if (!dialog.isShowing() && dialog != null) {
            dialog.show();
        }
    }

    /*更新大字的数据*/
    public void updateLarge(String tv) {
        tvLarge.setText(tv);
//        codeTv.invalidate();
        if (!dialog.isShowing() && dialog != null) {
            dialog.show();
        }
    }


    public void closeDia() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }


    public void disDia() {
        dialog.dismiss();
    }


}
