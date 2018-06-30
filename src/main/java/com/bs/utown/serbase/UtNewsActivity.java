package com.bs.utown.serbase;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.bs.utown.R;
import com.bs.utown.base.BaseActivity;
import com.bs.utown.fragment.UtNewsFragment;

import java.util.ArrayList;
import java.util.List;

public class UtNewsActivity extends BaseActivity {
    private TabLayout tabLayout;
    private ViewPager vp;
    private List<Fragment> fragments = new ArrayList<>();
    private String[] tabs = {"园区动态", "行业动态", "企业动态"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ut_news);
        baseapp.addActivity(this);
        initView();
        initTab();
    }

    private void initTab() {
        tabLayout.setTabMode(TabLayout.MODE_FIXED);

        fragments.add(new UtNewsFragment());
        fragments.add(new UtNewsFragment());
        fragments.add(new UtNewsFragment());
        vp.setAdapter(new TabAdapter(getSupportFragmentManager()));

        tabLayout.addOnTabSelectedListener(tabSelect);
        tabLayout.setupWithViewPager(vp);
    }

    private void initView() {
        findViewById(R.id.back_utnews).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                baseapp.finishActivity();
            }
        });
        tabLayout = (TabLayout) findViewById(R.id.utnews_tab);
        vp = (ViewPager) findViewById(R.id.utnews_vp);
    }

    /*TabLayout改变状态的监听器*/
    TabLayout.OnTabSelectedListener tabSelect = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            vp.setCurrentItem(tab.getPosition());
//            Logs.v("滑动选择：" + tab.getPosition());
//            fragments.get(tab.getPosition()).onResume();//滑动时刷新数据
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
