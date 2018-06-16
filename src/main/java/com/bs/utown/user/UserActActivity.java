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
import com.bs.utown.fragment.ActFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: 活动管理
 * AUTHOR: Champion Dragon
 * created at 2018/6/11
 **/
public class UserActActivity extends BaseActivity implements View.OnClickListener {

    private TabLayout tabLayout;
    private ViewPager vp;
    private List<Fragment> fragments = new ArrayList<>();
    private String[] tabs = {"已报名", "过往活动"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseapp.addActivity(this);
        setContentView(R.layout.activity_user_act);
        initView();
        initTab();
    }

    private void initTab() {
        tabLayout.setTabMode(TabLayout.MODE_FIXED);

        fragments.add(new ActFragment());
        fragments.add(new ActFragment());
        vp.setAdapter(new TabAdapter(getSupportFragmentManager()));

        tabLayout.addOnTabSelectedListener(tabSelect);
        tabLayout.setupWithViewPager(vp);
    }

    private void initView() {
        findViewById(R.id.back_useract).setOnClickListener(this);
        tabLayout = (TabLayout) findViewById(R.id.useract_tab);
        vp = (ViewPager) findViewById(R.id.useract_vp);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_useract:
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
