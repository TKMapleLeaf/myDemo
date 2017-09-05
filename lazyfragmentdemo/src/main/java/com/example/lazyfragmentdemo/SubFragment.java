package com.example.lazyfragmentdemo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Administrator on 2017/8/28.
 */

public class SubFragment extends Fragment {
    private List<String> mPhotos;
    private String mTagName;
    private SwipeRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private MyAdapter mAdapter;
    private Handler mHandler = new Handler(Looper.getMainLooper());

    public static Fragment getInstance(List<String> photos, String name) {
        SubFragment fragment = new SubFragment();
        fragment.mPhotos = photos;
        fragment.mTagName = name;
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d("TAG", mTagName + " onCreate() --> isVisibleToUser = "+ getUserVisibleHint());
        DisplayMetrics dm = SubFragment.this.getActivity().getResources().getDisplayMetrics();
        Log.e("TAG",dm.heightPixels+"");
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("TAG", mTagName + " onCreateView()");
        DisplayMetrics dm = SubFragment.this.getActivity().getResources().getDisplayMetrics();
        Log.e("TAG",dm.heightPixels+"");
        return inflater.inflate(R.layout.fragment_sub, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("TAG", mTagName + " onViewCreated()");
        DisplayMetrics dm = SubFragment.this.getActivity().getResources().getDisplayMetrics();
        Log.e("TAG",dm.heightPixels+"");
        mRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.mRefresh);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.mRecyclerView);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("TAG", mTagName + " onActivityCreated()");

        mAdapter = new MyAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),1));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("TAG", mTagName + " onStart()");
        mRefreshLayout.setRefreshing(true);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });

        if (getUserVisibleHint()){
            Log.d("TAG", mTagName + " getUserVisibleHint() --> isVisibleToUser = " + getUserVisibleHint());
        }
    }

    private void getData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (mRefreshLayout != null){
                                mRefreshLayout.setRefreshing(false);
                                if (mAdapter != null){
                                    mAdapter.addData(mPhotos);
                                }
                            }
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser){
            getData();
        }
        super.setUserVisibleHint(isVisibleToUser);
        Log.d("TAG", mTagName + " setUserVisibleHint() --> isVisibleToUser = " + isVisibleToUser);

    }
}
