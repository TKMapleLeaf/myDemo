package com.example.moments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.example.moments.basemvp.MvpBaseActivity;
import com.example.moments.bean.DynamicBean;
import com.example.moments.presenter.DynamicPresenter;
import com.example.moments.util.LogUtil;
import com.example.moments.view.DynamicView;

import java.util.List;

public class Main2Activity extends MvpBaseActivity<DynamicView, DynamicPresenter> implements BaseQuickAdapter.RequestLoadMoreListener, DynamicView {

    private RecyclerView mRecyclerView;
    private DynamicAdapter1 mDynamicAdapter;
    private SwipeRefreshLayout mRefreshLayout;
    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moments);


        initView();
        initAdapter();
//        addHeadView();
        initListener();

        initData();
    }

    private void initData() {
        mRefreshLayout.setRefreshing(true);
        getPresenter().dynamic("1");
    }

    @Override
    public DynamicPresenter createPresneter() {
        return new DynamicPresenter();
    }

    @Override
    public DynamicView createView() {
        return this;
    }

    private void initView() {
        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh);
        mRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initAdapter() {
        mDynamicAdapter = new DynamicAdapter1(R.layout.item_dynamic1);
        mDynamicAdapter.setOnLoadMoreListener(this, mRecyclerView);
        mDynamicAdapter.openLoadAnimation();
        mRecyclerView.setAdapter(mDynamicAdapter);

        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(final BaseQuickAdapter adapter, final View view, final int position) {
                Toast.makeText(Main2Activity.this, Integer.toString(position), Toast.LENGTH_LONG).show();
            }
        });
    }


    private void initListener() {

        mDynamicAdapter.setEnableLoadMore(false);

        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                getPresenter().dynamic(page + "");
                mDynamicAdapter.setEnableLoadMore(true);
            }
        });
    }

    @Override
    public void onLoadMoreRequested() {
        mRefreshLayout.setEnabled(false);
        getPresenter().dynamic(page + "");

    }

    @Override
    public void success(Object o) {

        resetListener();

        LogUtil.e("haha", "" + page);
        if (o instanceof DynamicBean) {
            DynamicBean bean = (DynamicBean) o;
            List<DynamicBean.Dynamic> dynamic = bean.getDynamic();
            if (dynamic != null && dynamic.size() > 0) {


                if (page == 1){
                    onRefreshSuccess(dynamic);
                }else {
                    onLoadMoreSuccess(dynamic);
                }
                page++;

            } else {
                mDynamicAdapter.loadMoreEnd(false);//true is gone,false is visible
            }
        }
    }

    @Override
    public void failure(String err) {
        resetListener();
        mDynamicAdapter.loadMoreFail();//加载失败
    }

    private void resetListener() {
        mRefreshLayout.setRefreshing(false);
        mRefreshLayout.setEnabled(true);

//        mDynamicAdapter.setEnableLoadMore(true);
    }

    public void onRefreshSuccess(List<DynamicBean.Dynamic> dynamic){
        mDynamicAdapter.setNewData(dynamic);
//        mDynamicAdapter.replaceData(dynamic);
        checkLoadMore(dynamic);
    }

    public void onLoadMoreSuccess(List<DynamicBean.Dynamic> dynamic){
        mDynamicAdapter.addData(dynamic);
        checkLoadMore(dynamic);
    }

    public void checkLoadMore(List<DynamicBean.Dynamic> dynamic) {
        if (dynamic.size() < 5) {
            mDynamicAdapter.loadMoreEnd(false);
        } else {
            mDynamicAdapter.loadMoreComplete();
        }
    }
}
