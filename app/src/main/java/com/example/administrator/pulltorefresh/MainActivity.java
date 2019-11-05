package com.example.administrator.pulltorefresh;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.example.administrator.pulltorefresh.refresh_load.BaseRefreshListener;
import com.example.administrator.pulltorefresh.refresh_load.PullToRefreshLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements BaseRefreshListener {

    private PullToRefreshLayout refreshLayout;
    private ListView pulllistView;
    private PullAdapter pullAdapter;
    private List<String> datas = new ArrayList<>();
    private  int currentPage=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化假数据数据
        for (int i = 1;i<6;i++){
            datas.add(""+i);
        }

        //初始化界面
        initView();

    }

    private void initView(){


        //刷新控件初始化
        refreshLayout = (PullToRefreshLayout)findViewById(R.id.mypullid);
        //设置监听，继承 BaseRefreshListener，导入两个方法 refresh()，loadMore()
        refreshLayout.setRefreshListener(this);
        pulllistView = (ListView)findViewById(R.id.pulllistviewid);
        pullAdapter = new PullAdapter(MainActivity.this,datas);
        pulllistView.setAdapter(pullAdapter);

    }

    private void initData(){


    }


    /**
     * 这里是模拟请求接口返回来的数据
     * */
    private void addListData(int page){

        if (page==1){
            datas.clear();
            for (int i = 1;i<6;i++){
                datas.add(""+i);
            }
        }else {
            List<String> ds = new ArrayList<>();
            for (int i = datas.size()+1;i<datas.size()+5;i++){
                ds.add(""+i);
            }
            datas.addAll(ds);
        }

        sendHandlerMessage(1,"");

    }
    //刷新UI使用handler
    private void sendHandlerMessage(int what, Object obj) {
        Message msg = new Message();
        msg.what = what;
        msg.obj = obj;
        handler.sendMessage(msg);
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @SuppressLint({"HandlerLeak", "ShowToast"})
        public void handleMessage(Message msg) {
            Log.e("come in", "");
            switch (msg.what) {
                case 1:
                    pullAdapter.notifyDataSetChanged();
                    break;

                default:

            }
        }
    };


    /**
     * 上拉刷新监听
     * */
    @Override
    public void refresh() {
        currentPage = 1;
        addListData(currentPage);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshLayout.finishRefresh();
            }
        }, 2000);//时间值，根据接口返回速度定
    }
    /**
     *下拉加载监听
     * */
    @Override
    public void loadMore() {
        currentPage++;
        addListData(currentPage);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshLayout.finishLoadMore();
            }
        }, 2000);//时间值，根据接口返回速度定
    }
}
