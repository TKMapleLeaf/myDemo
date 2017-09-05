package com.example.lazyfragmentdemo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2017/8/28.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mList;
    private List<String> mTitles;

    public ViewPagerAdapter(FragmentManager fm, List<Fragment> mList, List<String> mTitles) {
        super(fm);
        this.mList = mList;
        this.mTitles = mTitles;
    }

    @Override
    public Fragment getItem(int position) {
        return mList.get(position);
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles == null ? "" : mTitles.get(position);
    }
}
