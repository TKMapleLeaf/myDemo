package com.example.lazyfragmentdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2017/8/28.
 */

public class ViewPagerFragment extends Fragment {

    private int mFragmentType;

    public static final int TYPE_0 = 0;
    public static final int TYPE_1 = 1;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    public static ViewPagerFragment getInstance(int type){
        ViewPagerFragment fragment = new ViewPagerFragment();
        fragment.mFragmentType = type;
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_pager, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTabLayout = (TabLayout) view.findViewById(R.id.mSubTabLayout);
        mViewPager = (ViewPager) view.findViewById(R.id.mSubViewPager);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        List<Fragment> fragmentList = new ArrayList<>();
        List<String> mTitltes;
        ViewPagerAdapter pagerAdapter;
        switch (mFragmentType){
            case TYPE_0:
                mTitltes = Arrays.asList(getResources().getStringArray(R.array.titles1));
                fragmentList.add(SubFragment.getInstance(Arrays.asList(getResources().getStringArray(R.array.beauty)),"美女页"));
                fragmentList.add(SubFragment.getInstance(Arrays.asList(getResources().getStringArray(R.array.hansome)), "帅哥页"));
                fragmentList.add(SubFragment.getInstance(Arrays.asList(getResources().getStringArray(R.array.baby)), "萌娃页"));
                pagerAdapter = new ViewPagerAdapter(getChildFragmentManager(),fragmentList,mTitltes);
                mViewPager.setAdapter(pagerAdapter);
                mTabLayout.setupWithViewPager(mViewPager);
                break;
            case TYPE_1:
                mTitltes = Arrays.asList(getResources().getStringArray(R.array.titles2));
                fragmentList.add(SubFragment.getInstance(Arrays.asList(getResources().getStringArray(R.array.peking)), "北京页"));
                fragmentList.add(SubFragment.getInstance(Arrays.asList(getResources().getStringArray(R.array.hongkong)), "香港页"));
                fragmentList.add(SubFragment.getInstance(Arrays.asList(getResources().getStringArray(R.array.shanghai)), "上海页"));
                pagerAdapter = new ViewPagerAdapter(getChildFragmentManager(),fragmentList,mTitltes);
                mViewPager.setAdapter(pagerAdapter);
                mTabLayout.setupWithViewPager(mViewPager);
                break;
        }

    }
}
