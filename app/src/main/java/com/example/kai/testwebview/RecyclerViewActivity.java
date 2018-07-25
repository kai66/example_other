package com.example.kai.testwebview;

import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.progresslayout.ProgressLayout;

import java.util.ArrayList;
import java.util.zip.Inflater;

import Adapter.ExampleAdapter;
import Adapter.ProblemAdapter;
import Adapter.ScienceAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAOnItemChildClickListener;
import cn.bingoogolapple.androidcommon.adapter.BGAOnRVItemClickListener;
import entity.ExampleData;
import entity.ProblemData;
import entity.ScienceData;

/**
 * Created by kai on 2018/3/12.
 */

public class RecyclerViewActivity extends AppCompatActivity {

    View mHeadView;
    TwinklingRefreshLayout twinklingRefreshLayout;
    RecyclerView example_recycleview;
    ExampleAdapter exampleAdapter;
    RecyclerView science_recycleview;
    RecyclerView problem_recycleview;

    private LinearLayout pie_layout;

    private ProblemAdapter problemAdapter;
    private ScienceAdapter scienceAdapter;

    private ArrayList<ExampleData> mExampleDatas = new ArrayList<>();
    private ArrayList<ScienceData> mScienceDatas = new ArrayList<>();
    private ArrayList<ProblemData> mProblemDatas = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        initView();
        initAdapter();
        initData();
    }

    private void initView(){
        pie_layout = (LinearLayout)findViewById(R.id.pie_layout);
        twinklingRefreshLayout = (TwinklingRefreshLayout)findViewById(R.id.refresh_layout);
        example_recycleview = (RecyclerView)findViewById(R.id.example_recycleview);
        ProgressLayout headerView = new ProgressLayout(RecyclerViewActivity.this);
        twinklingRefreshLayout.setHeaderView(headerView);
        twinklingRefreshLayout.setFloatRefresh(true);
        twinklingRefreshLayout.setEnableOverScroll(false);
        twinklingRefreshLayout.setHeaderHeight(100);
        twinklingRefreshLayout.setMaxHeadHeight(120);
        twinklingRefreshLayout.setBottomHeight(40);
        twinklingRefreshLayout.setMaxBottomHeight(80);
        twinklingRefreshLayout.setTargetView(example_recycleview);
        twinklingRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
            }
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
            }
        });
    }

    private Button bt_one;
    private Button bt_two;
    private LinearLayoutManager mGlobalLLmanager;

    private void initAdapter(){
        mHeadView = LayoutInflater.from(RecyclerViewActivity.this).inflate(R.layout.activity_recycleview_headview, null);
        science_recycleview = mHeadView.findViewById(R.id.science_recycleview);
        problem_recycleview = mHeadView.findViewById(R.id.problem_recycleview);

        bt_one = (Button)mHeadView.findViewById(R.id.bt_one);
        bt_two = (Button)mHeadView.findViewById(R.id.bt_two);

        example_recycleview.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Log.v("TAG","kevin newState="+newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Log.v("TAG","kevin dx="+dx+"  dy="+dy);
            }
        });

        mGlobalLLmanager = new LinearLayoutManager(RecyclerViewActivity.this, LinearLayoutManager.VERTICAL, false);
        example_recycleview.setLayoutManager(mGlobalLLmanager);

        exampleAdapter = new ExampleAdapter(example_recycleview);
        exampleAdapter.addHeaderView(mHeadView);
        example_recycleview.setAdapter(exampleAdapter.getHeaderAndFooterAdapter());

        exampleAdapter.setOnRVItemClickListener(new BGAOnRVItemClickListener() {
            @Override
            public void onRVItemClick(ViewGroup parent, View itemView, int position) {
                Toast.makeText(RecyclerViewActivity.this, exampleAdapter.getData().get(position).getContent(),Toast.LENGTH_SHORT).show();
            }
        });

        problemAdapter = new ProblemAdapter(problem_recycleview);
        scienceAdapter = new ScienceAdapter(science_recycleview);
        LinearLayoutManager mGlobalLLmanager01 = new LinearLayoutManager(RecyclerViewActivity.this, LinearLayoutManager.VERTICAL, false);
        science_recycleview.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.left = 5;
                outRect.right = 5;
                outRect.bottom = 5;
                if (parent.getChildAdapterPosition(view) == 0) {
                    outRect.top = 5;
                }
            }
        });
        science_recycleview.setLayoutManager(mGlobalLLmanager01);
        LinearLayoutManager mGlobalLLmanager02 = new LinearLayoutManager(RecyclerViewActivity.this, LinearLayoutManager.VERTICAL, false);
        problem_recycleview.setLayoutManager(mGlobalLLmanager02);

        science_recycleview.setAdapter(scienceAdapter);
        problem_recycleview.setAdapter(problemAdapter);

        problemAdapter.setOnRVItemClickListener(new BGAOnRVItemClickListener() {
            @Override
            public void onRVItemClick(ViewGroup parent, View itemView, int position) {
                Toast.makeText(RecyclerViewActivity.this, problemAdapter.getData().get(position).getContent()+ position,Toast.LENGTH_SHORT).show();
            }
        });

        scienceAdapter.setOnItemChildClickListener(new BGAOnItemChildClickListener() {
            @Override
            public void onItemChildClick(ViewGroup parent, View childView, int position) {
                int id = childView.getId();
                //Log.v("TAG","kevin childView="+childView);
                switch (id){
                    case R.id.tv_example:
                        Toast.makeText(RecyclerViewActivity.this, scienceAdapter.getData().get(position).getContent(),Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.bt_delete:
                        scienceAdapter.getData().remove(position);
                        scienceAdapter.notifyDataSetChanged();
                        break;
                    default:
                        break;
                }
            }
        });

        /*
        scienceAdapter.setOnRVItemClickListener(new BGAOnRVItemClickListener() {
            @Override
            public void onRVItemClick(ViewGroup parent, View itemView, int position) {
                //Toast.makeText(RecyclerViewActivity.this, scienceAdapter.getData().get(position).getContent()+ position,Toast.LENGTH_SHORT).show();
            }
        });
        */
    }

    private void initData(){
        //twinklingRefreshLayout.startRefresh();
        for(int i =0;i<30;i++) {
            ExampleData exampleData = new ExampleData();
            exampleData.setContent("example-"+ i);
            mExampleDatas.add(exampleData);
        }
        exampleAdapter.setData(mExampleDatas);
        for (int j=0;j<20;j++) {
            ScienceData scienceData = new ScienceData();
            scienceData.setContent("science-"+j);
            mScienceDatas.add(scienceData);
        }
        scienceAdapter.setData(mScienceDatas);
        for(int k=0;k<15;k++) {
            ProblemData problemData = new ProblemData();
            problemData.setContent("problem-"+k);
            mProblemDatas.add(problemData);
        }
        problemAdapter.setData(mProblemDatas);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
