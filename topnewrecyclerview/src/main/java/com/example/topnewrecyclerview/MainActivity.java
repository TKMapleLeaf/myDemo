package com.example.topnewrecyclerview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements MyRecyclerView.onGetListener {


    private List<Integer> lists = new ArrayList<>();
    private MyRecyclerView recyclerView;
    private RecyclerAdapter adapter;
    private LinearLayoutManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initviews();
    }

    private void initviews() {
        recyclerView = (MyRecyclerView) findViewById(R.id.my_recycler);
        adapter = new RecyclerAdapter(getApplicationContext(), lists);
        manager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        recyclerView.setListener(this);

    }

    private void initData() {
        for (int i = 1; i < 20; i++) {
            lists.add(i);
        }
    }


    @Override
    public void getPosition(int position) {

        Toast.makeText(getApplicationContext(), "现在是第" + String.valueOf(position + 1) + "项", Toast.LENGTH_SHORT).show();
    }
}
