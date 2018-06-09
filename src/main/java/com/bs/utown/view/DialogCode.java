package com.bs.utown.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

/**
 * Description: 二维码名片的对话框
 * AUTHOR: Champion Dragon
 * created at 2018/6/4
 **/
public class DialogCode {
    private Context context;
    private Dialog dialog;
    private ImageView codeIv;
    private RoundImageView head;
    private TextView TVname, TVuid;

    public DialogCode(Context context, String name, String uid, String headPath, String codeStr, final DiadisListener listener) {
        this.context = context;
        dialog = new Dialog(context, R.style.dialog);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_code, null);
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        TVname = view.findViewById(R.id.code_name);
        TVuid = view.findViewById(R.id.code_uid);
        codeIv = view.findViewById(R.id.code_iv);
        head = view.findViewById(R.id.code_ri);

        if (headPath != null && !headPath.isEmpty()) {
            head.setImageBitmap(BitmapFactory.decodeFile(headPath));
        }

        if (codeStr == null) {
            codeIv.setImageBitmap(getBitamap("欢迎来到优唐智慧园"));
        } else {
            codeIv.setImageBitmap(getBitamap(codeStr));
        }
        TVname.setText(name);
        TVuid.setText(uid);

//        view.findViewById(R.id.code_iv).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                listener.dismiss();
//            }
//        });
        dialog.setContentView(view, new LinearLayout.LayoutParams(
                dm.widthPixels * 4 / 5,//LinearLayout.LayoutParams.MATCH_PARENT
                dm.heightPixels * 3 / 5));
        dialog.show();

        dialog.setCancelable(true);//点击空白处令弹框小时
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                listener.dismiss();
            }
        });

    }

    /*生成二维码的bitmap*/
    private Bitmap getBitamap(String str) {
        Bitmap qrCodeBitmap = CreateCodeUtil.createQRCode(str, DPUtil.dip2px(context, 300),
                DPUtil.dip2px(context, 300), null);
        return qrCodeBitmap;

    }

    public void closeDia() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }

}
