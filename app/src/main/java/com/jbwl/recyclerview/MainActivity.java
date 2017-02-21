package com.jbwl.recyclerview;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<String> dataList = new ArrayList<>();
    private LoadMoreRecyclerView loadMoreRecyclerView;
    private MyAdapter myAdapter;
    private SwipeRefreshLayout srl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        initView();
        initData();
        initListener();
    }

    private void initView() {
        srl = (SwipeRefreshLayout) findViewById(R.id.srl);
        srl.setColorSchemeResources(android.R.color.holo_red_light, android.R.color.holo_blue_light, android.R.color.holo_green_light);
        loadMoreRecyclerView = (LoadMoreRecyclerView) findViewById(R.id.rv);
        loadMoreRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        loadMoreRecyclerView.setLoadMoreEnable(true);//允许加载更多
        loadMoreRecyclerView.setFooterResource(R.layout.item_footer);//设置脚布局
        myAdapter = new MyAdapter(dataList);
        loadMoreRecyclerView.setAdapter(myAdapter);
    }

    private void initData() {
        for (int i = 0; i < 10; i++) {
            dataList.add("第"+(i+1)+"个条目");
        }
        loadMoreRecyclerView.notifyData();
    }

    private void initListener() {
        loadMoreRecyclerView.setOnLoadMoreListener(new LoadMoreRecyclerView.OnLoadMoreListener() {
            @Override
            public void loadMoreListener() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < 10; i++) {
                            dataList.add("更多条目" + i);
                        }
                        loadMoreRecyclerView.notifyData();//刷新数据
                    }
                }, 3000);
            }
        });
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        srl.setRefreshing(false);
                        dataList.clear();
                        dataList.add("第一条目");
                        dataList.add("第二条目");
                        dataList.add("第三条目");
                        dataList.add("第四条目");
                        dataList.add("第五条目");
                        dataList.add("第六条目");
                        dataList.add("第七条目");
                        dataList.add("第八条目");
                        loadMoreRecyclerView.notifyData();//刷新数据
                    }
                }, 1500);
            }
        });
    }


    private Handler handler = new Handler();


}
