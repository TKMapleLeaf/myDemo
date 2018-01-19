package com.example.demo;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private Fragment[] fragments;
    private DefaultFragment fragment1;
    private DefaultFragment fragment2;
    private DefaultFragment fragment3;
    private DefaultFragment fragment4;
    private int lastShowFragment = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFragments();
        BottomNavigationView navigationView = findViewById(R.id.navigation);
        navigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
        BottomNavigationViewHelper.disableShiftMode(navigationView);
    }

    private void initFragments() {
        fragment1 = new DefaultFragment();
        fragment2 = new DefaultFragment();
        fragment3 = new DefaultFragment();
        fragment4 = new DefaultFragment();
        fragments = new Fragment[]{fragment1, fragment2, fragment3, fragment4};
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, fragment1)
                .show(fragment1)
                .commit();
    }


    private void switchFrament(int lastShowFragment, int i) {

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.hide(fragments[lastShowFragment]);
        if (!fragments[i].isAdded()) {
            ft.add(R.id.fragment_container, fragments[i]);
        }
        ft.show(fragments[i]).commitAllowingStateLoss();
    }


    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    if (lastShowFragment != 0) {
                        switchFrament(lastShowFragment, 0);
                        lastShowFragment = 0;
                    }
                    return true;
                case R.id.navigation_dashboard:
                    if (lastShowFragment != 1) {
                        switchFrament(lastShowFragment, 1);
                        lastShowFragment = 1;
                    }
                    return true;
                case R.id.navigation_notifications:
                    if (lastShowFragment != 2) {
                        switchFrament(lastShowFragment, 2);
                        lastShowFragment = 2;
                    }
                    return true;
                case R.id.navigation_4:
                    if (lastShowFragment != 3) {
                        switchFrament(lastShowFragment, 3);
                        lastShowFragment = 3;
                    }
                    return true;
                default:
                    break;

            }
            return false;
        }
    };

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View view = getCurrentFocus();
            if (isShouldHideKeyboard(view, ev)) {
                InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (im != null) {
                    im.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }

        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘
     *
     * @param view
     * @param event
     * @return
     */
    private boolean isShouldHideKeyboard(View view, MotionEvent event) {
        if (view != null && (view instanceof EditText)) {
            int[] l = {0, 0};
            view.getLocationInWindow(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + view.getHeight(),
                    right = left + view.getWidth();
            return !(event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom);
        }
        return false;
    }
}
