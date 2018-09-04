package com.bs.utown.sercompany;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bs.utown.R;
import com.bs.utown.base.BaseActivity;
import com.bs.utown.bean.AdmissionBean;
import com.bs.utown.constant.Constant;
import com.bs.utown.constant.SpKey;
import com.bs.utown.util.HttpByGet;
import com.bs.utown.util.SmallUtil;
import com.bs.utown.util.ToastUtil;
import com.bs.utown.view.DialogNoticeUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Description:提交申请
 * AUTHOR: Champion Dragon
 * created at 2018/7/6
 **/
public class AdmissionSubmitActivity extends BaseActivity implements View.OnClickListener {
    private TextView name, time, user, phone, email, type, bussiness, tv;
    private ImageView iv;
    private File image;
    private String tag = "AdmissionSubmitActivity";
    private AdmissionBean bean;//得到入驻申请的参数
    private static final int SUCCESS = 40;
    private static final int FAIL = 41;
    private static final int ERROR = 42;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admission_submit);
        baseapp.TemaddActivity(this);
        initView();
    }

    private void initView() {
        findViewById(R.id.back_admissionsubmit).setOnClickListener(this);
        findViewById(R.id.admissionsubmit_submit).setOnClickListener(this);
        name = (TextView) findViewById(R.id.admissionsubmit_name);
        time = (TextView) findViewById(R.id.admissionsubmit_time);
        user = (TextView) findViewById(R.id.admissionsubmit_user);
        phone = (TextView) findViewById(R.id.admissionsubmit_phone);
        email = (TextView) findViewById(R.id.admissionsubmit_email);
//        type = (TextView) findViewById(R.id.admissionsubmit_type);
//        bussiness = (TextView) findViewById(R.id.admissionsubmit_bussiness);
        tv = (TextView) findViewById(R.id.admissionsubmit_tv);
        iv = (ImageView) findViewById(R.id.admissionsubmit_iv);

        Bundle bundle = getIntent().getExtras();
        bean = (AdmissionBean) bundle.getSerializable(SpKey.admissionBean);
        name.setText(bean.getName());
        time.setText(bean.getTime());
        user.setText(bean.getUser());
        phone.setText(bean.getPhone());
        email.setText(bean.getEmail());
//        type.setText(bean.getType());
//        bussiness.setText(bean.getBussiness());


        image = new File(baseapp.sp.getString(SpKey.licensePath));
        if (image.exists()) {
            iv.setImageBitmap(BitmapFactory.decodeFile(image.getAbsolutePath()));
            tv.setVisibility(View.GONE);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_admissionsubmit:
                baseapp.TemfinishActivity();
                break;
            case R.id.admissionsubmit_submit:
                Submit();
                break;
        }
    }

    /*提交后的一些操作*/
    private void Submit() {
        if (image.exists()) {
            image.delete();
        }
        executor.submit(SubmitRunnable);
    }

    /*向后台发送申请的数据*/
    private Runnable SubmitRunnable = new Runnable() {
        @Override
        public void run() {
            String url = Constant.Apply + HttpByGet.get("g", "Api", "m", "Wechat", "a", "apply_info", "company_name", getEncoder(bean.getName()),
                    "enter_date", bean.getTime(), "username", getEncoder(bean.getUser()), "phone", bean.getPhone(), "email", bean.getEmail());
            String result = HttpByGet.executeHttpGet(url);
            if (result.equals(HttpByGet.error)) {
                handler.sendEmptyMessage(ERROR);
                return;
            } else {
                try {
                    JSONObject js = new JSONObject(result);
                    if (js.getString("result").equals("ok")) {
                        handler.sendEmptyMessage(SUCCESS);
                    } else {
                        handler.sendEmptyMessage(FAIL);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    };


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SUCCESS:
                    ToastUtil.showLong("入驻申请成功,请耐心等待审核");
                    SmallUtil.getActivity(AdmissionSubmitActivity.this, AdmissionSuccessActivity.class);
                    break;
                case FAIL:
                    DialogNoticeUtil.show(AdmissionSubmitActivity.this, "该手机号码已申请入驻");
                    break;
                case ERROR:
                    DialogNoticeUtil.show(AdmissionSubmitActivity.this, "访问后台失败");
                    break;
            }
        }
    };


    /*解决URL中的中文字符转换*/
    private String getEncoder(String str) {
        try {
            str = URLEncoder.encode(str, "utf-8");// URL编码
            // 它是一种编码类型。当URL地址里包含非西欧字符的字符串时，系统会将这些字符转换成application/x-www-form-urlencoded字符串。
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return str;
    }

}
