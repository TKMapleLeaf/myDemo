package com.example.topnewrecyclerview;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.topnewrecyclerview.refresh.MyRefreshRecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SecondActivity extends AppCompatActivity {

    private SwipeRefreshLayout swipeRefreshLayout;
    private MyRefreshRecyclerView recyclerView;
    private List<String> integerList = new ArrayList<>();
    private MyAdapter myAdapter;
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        init();
    }

    private void init() {
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
        recyclerView = (MyRefreshRecyclerView) findViewById(R.id.recycler);
        myAdapter = new MyAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        swipeRefreshLayout.setColorSchemeResources(R.color.red,
                R.color.orange,
                R.color.green,
                R.color.blue);

        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });
        refresh();


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });

        recyclerView.setMyLoadListener(new MyRefreshRecyclerView.MyLoadListener() {
            @Override
            public void onLoadMore() {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (integerList.size() > 14) {
                            recyclerView.setLoadMore(true);
                        } else {
                            int randomInt = new Random().nextInt(100);
                            integerList.add("上拉加载添加数字:" + randomInt);
                            myAdapter.notifyDataSetChanged();
                            recyclerView.setLoadMore(false);
                        }

                    }
                }, 1000);
            }
        });
    }

    private void getData() {
        integerList.clear();
        Random random = new Random();
        while (integerList.size() < 12) {
            int randomInt = random.nextInt(100);
            integerList.add(String.valueOf(randomInt));
        }
    }

    private void refresh() {

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getData();
                recyclerView.setAdapter(myAdapter);
                myAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 1500);

    }


    public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_refresh_recycler, parent, false);

            return new MyViewHolder(rootView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.txt.setText(integerList.get(position));
        }

        @Override
        public int getItemCount() {
            return integerList.size();
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txt;

        public MyViewHolder(View itemView) {
            super(itemView);
            txt = (TextView) itemView.findViewById(R.id.item_txt);
        }
    }

}
