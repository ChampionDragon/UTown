package com.bs.utown;

import android.os.Bundle;
import android.view.View;

import com.bs.utown.base.BaseActivity;
import com.bs.utown.constant.Constant;
import com.bs.utown.constant.SpKey;
import com.bs.utown.serbase.UtActActivity;
import com.bs.utown.serbase.UtNewsActivity;
import com.bs.utown.serbase.UtNoticeActivity;
import com.bs.utown.sercompany.AdmissionCompActivity;
import com.bs.utown.sercompany.ExpansionActivity;
import com.bs.utown.sercompany.ResnsiteActivity;
import com.bs.utown.util.SmallUtil;

/**
 * Description: 所有服务的主页
 * AUTHOR: Champion Dragon
 * created at 2018/5/31
 **/
public class SerAllActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseapp.addActivity(this);
        setContentView(R.layout.activity_ser_all);
        initView();
    }

    private void initView() {
        findViewById(R.id.back_serall).setOnClickListener(this);
        findViewById(R.id.serall_officialweb).setOnClickListener(this);//官网
        findViewById(R.id.serall_news).setOnClickListener(this);//园区咨讯
        findViewById(R.id.serall_act).setOnClickListener(this);//园区活动
        findViewById(R.id.serall_notice).setOnClickListener(this);//园区公告
        findViewById(R.id.serall_admissioncomp).setOnClickListener(this);//企业入驻
        findViewById(R.id.serall_expansion).setOnClickListener(this);//扩租申请
        findViewById(R.id.serall_resnsite).setOnClickListener(this);//预定场地
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_serall:
                baseapp.finishActivity();
                break;
            case R.id.serall_news:
                SmallUtil.getActivity(SerAllActivity.this, UtNewsActivity.class);
                break;
            case R.id.serall_act:
                SmallUtil.getActivity(SerAllActivity.this, UtActActivity.class);
                break;
            case R.id.serall_notice:
                SmallUtil.getActivity(SerAllActivity.this, UtNoticeActivity.class);
                break;
            case R.id.serall_admissioncomp:
                SmallUtil.getActivity(SerAllActivity.this, AdmissionCompActivity.class);
                break;
            case R.id.serall_expansion:
                SmallUtil.getActivity(SerAllActivity.this, ExpansionActivity.class);
                break;
            case R.id.serall_resnsite:
                SmallUtil.getActivity(SerAllActivity.this, ResnsiteActivity.class);
                break;
            case R.id.serall_officialweb:
                Bundle bundle = new Bundle();
                bundle.putString(SpKey.webTv, "优唐智慧园");
                bundle.putString(SpKey.webUrl, Constant.urlBaiSheng);
                SmallUtil.getActivity(SerAllActivity.this, WebviewActivity.class, bundle);
                break;
        }
    }
}
