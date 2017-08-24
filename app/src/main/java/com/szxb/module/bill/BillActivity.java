package com.szxb.module.bill;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.szxb.R;
import com.szxb.base.BaseActivity;

import butterknife.BindView;

/**
 * 作者: Tangren on 2017/8/2
 * 包名：com.szxb.module.bill
 * 邮箱：996489865@qq.com
 * TODO:交易查询
 */

public class BillActivity extends BaseActivity {
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.home)
    Button home;
    @BindView(R.id.toolBarTitle)
    TextView toolBarTitle;

    private String[] titles = new String[]{"云端记录", "本地记录"};

    @Override
    protected int layoutID() {
        return R.layout.activity_bill;
    }

    @Override
    protected void initView() {
        toolBarTitle.setText("账单");
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishActivityFromRight();
            }
        });
    }

    @Override
    protected void initData() {
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return selectPosition(position);
            }

            @Override
            public int getCount() {
                return titles.length;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return titles[position];
            }
        });
        tabLayout.setupWithViewPager(viewPager);
    }

    private Fragment selectPosition(int position) {
        if (position == 0) {
            return NetWorkBillFragment.newInstance(position);
        } else {
            return LocalBIllFragment.newInstance(position);
        }
    }
}
