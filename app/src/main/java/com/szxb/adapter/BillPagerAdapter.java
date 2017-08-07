package com.szxb.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * 作者: Tangren on 2017/8/2
 * 包名：com.szxb.adapter
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */

public class BillPagerAdapter extends FragmentPagerAdapter {

    private String titles[] = new String[]{"云端记录", "本地记录"};

    public BillPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return titles.length;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
