package com.bs.utown.sercompany;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bs.utown.R;
import com.bs.utown.base.BaseActivity;
import com.bs.utown.bean.AdmissionBean;
import com.bs.utown.constant.Constant;
import com.bs.utown.constant.SpKey;
import com.bs.utown.listener.DiadisListener;
import com.bs.utown.pickerview.TimePickerView;
import com.bs.utown.pickerview.other.pickerViewUtil;
import com.bs.utown.util.HttpByGet;
import com.bs.utown.util.Logs;
import com.bs.utown.util.SmallUtil;
import com.bs.utown.util.ToastUtil;
import com.bs.utown.view.DialogIv;
import com.bs.utown.view.DialogNoticeUtil;
import com.bs.utown.view.DialogVp;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Description: 申请入驻
 * AUTHOR: Champion Dragon
 * created at 2018/6/27
 **/
public class AdmissionApplyActivity extends BaseActivity implements View.OnClickListener {
    private TextView time, phone, type, bussiness;
    private EditText name, user, email, desc, num;
    private DialogIv dialogIv;
    private DialogVp dialogVp;
    private String[] typeArray = {"移动工位", "固定工位"};
    private String[] bussinessArray = {"专属办公室", "定制设计运营服务"};
    private int markType = 0;
    private int markBussiness = 1;
    private CheckBox checkBox;
    private static final int SUCCESS = 46;
    private static final int FAIL = 47;
    private static final int ERROR = 48;
    private String tag = "AdmissionApplyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admission_apply);
        baseapp.TemaddActivity(this);
        initView();
    }

    private void initView() {
        findViewById(R.id.back_admissionapply).setOnClickListener(this);
        findViewById(R.id.admissionapply_terms).setOnClickListener(this);
        findViewById(R.id.admissionapply_next).setOnClickListener(this);
        findViewById(R.id.admissionapply_explanation).setOnClickListener(this);
        findViewById(R.id.admissionapply_time).setOnClickListener(this);
        checkBox = (CheckBox) findViewById(R.id.admissionapply_cb);
        time = (TextView) findViewById(R.id.admissionapply_timetv);
        name = (EditText) findViewById(R.id.admissionapply_name);
        user = (EditText) findViewById(R.id.admissionapply_user);
        desc = (EditText) findViewById(R.id.admissionapply_desc);
        num = (EditText) findViewById(R.id.admissionapply_num);
        phone = (TextView) findViewById(R.id.admissionapply_phone);
        /*由于手机号码必须是要用户注册的这个所以不允许更改，写死它*/
        phone.setText(baseapp.sp.getString(SpKey.UserPhone));
//        email = (EditText) findViewById(R.id.admissionapply_email);
//        type = (TextView) findViewById(R.id.admissionapply_type);
//        type.setOnClickListener(this);
//        bussiness = (TextView) findViewById(R.id.admissionapply_bussiness);
//        bussiness.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_admissionapply:
                baseapp.TemfinishActivity();
                break;
            case R.id.admissionapply_explanation:
                OpenDialog();
                break;
            case R.id.admissionapply_time:
                Time();
                break;
            case R.id.admissionapply_terms:
                /*跳转到协议界面*/
                SmallUtil.getActivity(AdmissionApplyActivity.this, TermsActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putString(SpKey.webTv, "用户协议");
//                bundle.putString(SpKey.webUrl, Constant.urlTerms);
//                SmallUtil.getActivity(AdmissionApplyActivity.this, WebviewActivity.class, bundle);
                break;
            case R.id.admissionapply_next:
                Logs.d("是否勾选上:" + checkBox.isChecked());
                if (checkBox.isChecked()) {
                } else {
                    DialogNoticeUtil.show(AdmissionApplyActivity.this, "请先阅读协议并打钩同意");
                    return;
                }
//                Next();//调到上传图片的界面
                executor.submit(SubmitRunnable);//直接向后台发送提交数据
                break;
//            case R.id.admissionapply_type:
//                List<Integer> listId = new ArrayList<>();
//                listId.add(R.mipmap.typemobile);
//                listId.add(R.mipmap.typefixed);
//                VpDialog(getIv(listId), markType);
//                break;
//            case R.id.admissionapply_bussiness:
//                List<Integer> list = new ArrayList<>();
//                list.add(R.mipmap.typeexclusive);
//                list.add(R.mipmap.typecustomer);
//                VpDialog(getIv(list), markBussiness);
//                break;
        }
    }

    /*创建自定义Imageview集合*/
    private List<ImageView> getIv(List<Integer> listId) {
        List<ImageView> list = new ArrayList<>();
        for (int a = 0; a < listId.size(); a++) {
            ImageView imageView = new ImageView(this);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageResource(listId.get(a));
            list.add(imageView);
        }
        return list;
    }


    //创建
    private void VpDialog(List<ImageView> list, final int mark) {
        if (dialogVp != null) {

        } else {
            dialogVp = new DialogVp(this, list, new DiadisListener() {
                @Override
                public void dismiss(Object object) {
                    if ((int) object != 99) {
                        if (mark == markBussiness) {
                            bussiness.setText(bussinessArray[(int) object]);
                        } else if (mark == markType) {
                            type.setText(typeArray[(int) object]);
                        }
                    }
                    closeDialog();
                }
            });
        }
    }


    //创建二维码弹框
    private void OpenDialog() {
        if (dialogIv != null) {
        } else {
            dialogIv = new DialogIv(this, R.mipmap.explan, new DiadisListener() {
                @Override
                public void dismiss(Object object) {
                    closeDialog();
                }
            });
        }
    }

    //关闭
    private void closeDialog() {
        if (dialogIv != null) {
            dialogIv.closeDia();
            dialogIv = null;
        }
        if (dialogVp != null) {
            dialogVp.closeDia();
            dialogVp = null;
        }
    }


    private void Time() {
        pickerViewUtil.alertTimerPicker(AdmissionApplyActivity.this, TimePickerView.Type.YEAR_MONTH_DAY, Constant.formatbusinesstime,
                "请选择入驻时间", 19, new pickerViewUtil.TimerPickerCallBack() {
                    @Override
                    public void onTimeSelect(String date) {
                        time.setText(date);
                        time.setTextColor(ContextCompat.getColor(AdmissionApplyActivity.this, R.color.black));
                    }
                });
    }

    private void Next() {
//        if (!checkEmail(email.getText().toString())) {
//            ToastUtil.showLong("输入的邮箱格式不对");
//            return;
//        }
        AdmissionBean bean = new AdmissionBean();
        bean.setName(name.getText().toString());
//        bean.setType(type.getText().toString());
//        bean.setBussiness(bussiness.getText().toString());
//        bean.setEmail(email.getText().toString());
        bean.setPhone(phone.getText().toString());
        bean.setTime(time.getText().toString());
        bean.setUser(user.getText().toString());
        Bundle bundle = new Bundle();
        bundle.putSerializable(SpKey.admissionBean, bean);
        SmallUtil.getActivity(AdmissionApplyActivity.this, AdmissionLicenseActivity.class, bundle);
    }

    /* 验证邮箱*/
    public boolean checkEmail(String email) {
        boolean flag = false;
        try {
            String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }


    /*向后台发送申请的数据*/
    private Runnable SubmitRunnable = new Runnable() {
        @Override
        public void run() {
            String url = Constant.Apply + HttpByGet.get("g", "Api", "m", "Wechat", "a", "apply_info", "company_name",
                    getEncoder(name.getText().toString()), "desc", getEncoder(desc.getText().toString()), "enter_number",
                    num.getText().toString(), "enter_date", time.getText().toString(),
                    "username", getEncoder(user.getText().toString()), "phone", phone.getText().toString());
            String result = HttpByGet.executeHttpGet(url);
            if (result.equals(HttpByGet.error)) {
                handler.sendEmptyMessage(ERROR);
                return;
            } else {
                try {
                    JSONObject js = new JSONObject(result);
                    if (js.getString("result").equals("ok")) {
                        handler.sendEmptyMessage(SUCCESS);
                    } else if (js.getString("result").equals("error")) {
                        Message msg = Message.obtain(handler, FAIL);
                        msg.obj = js.getString("msg");
                        handler.sendMessage(msg);
                    } else {
                        handler.sendEmptyMessage(ERROR);
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
                    SmallUtil.getActivity(AdmissionApplyActivity.this, AdmissionSuccessActivity.class);
                    break;
                case FAIL:
                    DialogNoticeUtil.show(AdmissionApplyActivity.this, (String) msg.obj);
                    break;
                case ERROR:
                    DialogNoticeUtil.show(AdmissionApplyActivity.this, "访问后台失败");
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
            Logs.e(tag + "299:" + e);
        }
        return str;
    }


}
