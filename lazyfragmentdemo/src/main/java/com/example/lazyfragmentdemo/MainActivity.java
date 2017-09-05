package com.example.lazyfragmentdemo;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    private TabLayout mTabLayout;
    private RelativeLayout mContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mTabLayout = (TabLayout) findViewById(R.id.mTabLayout);
        mContainer = (RelativeLayout) findViewById(R.id.mContainer);

        initTabLayout();
    }

    private void initTabLayout() {
        mTabLayout.addTab(mTabLayout.newTab().setText("人物"));
        mTabLayout.addTab(mTabLayout.newTab().setText("风景"));
        setCurrentPage(0);
        mTabLayout.getTabAt(0).select();
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.d("TAB", "onTabSelected()" + tab.getPosition());
                setCurrentPage(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                Log.d("TAB", "onTabUnselected()" + tab.getPosition());
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                Log.d("TAB", "onTabReselected()" + tab.getPosition());
            }
        });
    }

    private void setCurrentPage(int position) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        switch (position){
            case 0:
                ft.replace(R.id.mContainer,ViewPagerFragment.getInstance(ViewPagerFragment.TYPE_0));
                ft.commit();
                break;
            case 1:
                ft.replace(R.id.mContainer,ViewPagerFragment.getInstance(ViewPagerFragment.TYPE_1));
                ft.commit();
                break;
        }

    }
}
