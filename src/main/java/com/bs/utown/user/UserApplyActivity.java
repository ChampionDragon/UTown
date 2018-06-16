package com.bs.utown.user;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.bs.utown.R;
import com.bs.utown.base.BaseActivity;
import com.bs.utown.fragment.ApplyAdmissionFragment;
import com.bs.utown.fragment.ApplyExpansionFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: 申请记录
 * AUTHOR: Champion Dragon
 * created at 2018/6/11
 **/
public class UserApplyActivity extends BaseActivity implements View.OnClickListener {
    private TabLayout tabLayout;
    private ViewPager vp;
    private List<Fragment> fragments = new ArrayList<>();
    private String[] tabs = {"入园申请", "扩租申请"};
    public static String expansion = "expansion";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseapp.addActivity(this);
        setContentView(R.layout.activity_user_apply);
        initView();
        initTab();
    }

    private void initTab() {
        tabLayout.setTabMode(TabLayout.MODE_FIXED);

        fragments.add(new ApplyAdmissionFragment());
        fragments.add(new ApplyExpansionFragment());
        vp.setAdapter(new TabAdapter(getSupportFragmentManager()));

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            vp.setCurrentItem(extras.getInt(expansion));
        }

        tabLayout.addOnTabSelectedListener(tabSelect);
        tabLayout.setupWithViewPager(vp);
    }

    private void initView() {
        findViewById(R.id.back_userapply).setOnClickListener(this);
        tabLayout = (TabLayout) findViewById(R.id.userapply_tab);
        vp = (ViewPager) findViewById(R.id.userapply_vp);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_userapply:
                baseapp.finishActivity();
                break;
        }
    }


    /*TabLayout改变状态的监听器*/
    TabLayout.OnTabSelectedListener tabSelect = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            vp.setCurrentItem(tab.getPosition());
//            Logs.v("滑动选择：" + tab.getPosition());
            fragments.get(tab.getPosition()).onResume();//滑动时刷新数据
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }

    };


    /* Fragment适配器*/
    class TabAdapter extends FragmentPagerAdapter {

        public TabAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        //显示标签上的文字
        @Override
        public CharSequence getPageTitle(int position) {
            return tabs[position];
        }
    }


}
