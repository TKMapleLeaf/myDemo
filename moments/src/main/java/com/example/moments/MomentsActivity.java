package com.example.moments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.example.moments.basemvp.MvpBaseActivity;
import com.example.moments.bean.DynamicBean;
import com.example.moments.presenter.DynamicPresenter;
import com.example.moments.util.LogUtil;
import com.example.moments.view.DynamicView;

import java.util.List;

public class MomentsActivity extends MvpBaseActivity<DynamicView, DynamicPresenter> implements BaseQuickAdapter.RequestLoadMoreListener, DynamicView {

    private RecyclerView mRecyclerView;
    private DynamicAdapter mDynamicAdapter;
    private SwipeRefreshLayout mRefreshLayout;
    private boolean isRefresh;
    private int page = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moments);


        initView();
        initAdapter();
        addHeadView();
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
        mDynamicAdapter = new DynamicAdapter(R.layout.item_dynamic);
        mDynamicAdapter.setOnLoadMoreListener(this, mRecyclerView);
        mDynamicAdapter.openLoadAnimation();
//        mDynamicAdapter.setPreLoadNumber(3);
        mRecyclerView.setAdapter(mDynamicAdapter);

        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(final BaseQuickAdapter adapter, final View view, final int position) {
                Toast.makeText(MomentsActivity.this, Integer.toString(position), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void addHeadView() {
        View headView = getLayoutInflater().inflate(R.layout.head_view, (ViewGroup) mRecyclerView.getParent(), false);
        headView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mLoadMoreEndGone = true;
//                mDynamicAdapter.setLoadMoreView(new CustomLoadMoreView());
//                mRecyclerView.setAdapter(mDynamicAdapter);
                Toast.makeText(MomentsActivity.this, "change complete", Toast.LENGTH_LONG).show();
            }
        });
        mDynamicAdapter.addHeaderView(headView);
    }

    private void initListener() {

        mDynamicAdapter.setEnableLoadMore(false);

        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
               /* new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mRefreshLayout.setRefreshing(false);

                    }
                }, 3000);*/

                isRefresh = true;
                page = 1;
                getPresenter().dynamic(page + "");

            }
        });
    }

    @Override
    public void onLoadMoreRequested() {
        mRefreshLayout.setEnabled(false);

           /* new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mDynamicAdapter.addData(mDynamic1);
                    mRefreshLayout.setEnabled(true);
                    mDynamicAdapter.loadMoreComplete();
//                        mDynamicAdapter.loadMoreFail();//
                }
            }, 1000);*/
        getPresenter().dynamic(page + "");

    }

    @Override
    public void success(Object o) {

        resetListener();

        LogUtil.e("haha", "" + isRefresh);
        if (o instanceof DynamicBean) {
            DynamicBean bean = (DynamicBean) o;
            List<DynamicBean.Dynamic> dynamic = bean.getDynamic();
            if (dynamic != null && dynamic.size() > 0) {

                page++;

                if (isRefresh) {
//                  mDynamicAdapter.replaceData(mDynamic);
                    mDynamicAdapter.setNewData(dynamic);//重新刷新，请用调用这个方法
                    isRefresh = false;
                } else {
                    mDynamicAdapter.addData(dynamic);
                    mDynamicAdapter.loadMoreComplete();//加载完成（注意不是加载结束，而是本次数据加载结束并且还有下页数据）
                }
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

        mDynamicAdapter.setEnableLoadMore(true);
    }

}
