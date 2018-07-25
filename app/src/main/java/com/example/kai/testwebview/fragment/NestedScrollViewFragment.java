package com.example.kai.testwebview.fragment;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.example.kai.testwebview.R;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.progresslayout.ProgressLayout;
import java.util.ArrayList;
import Adapter.ExampleAdapter;
import Adapter.ProblemAdapter;
import Adapter.ScienceAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAOnItemChildClickListener;
import cn.bingoogolapple.androidcommon.adapter.BGAOnRVItemClickListener;
import entity.ExampleData;
import entity.ProblemData;
import entity.ScienceData;

/**
 * Created by kai on 2018/5/22.
 */

public class NestedScrollViewFragment extends BaseFragment{

    TwinklingRefreshLayout twinklingRefreshLayout;
    RecyclerView example_recycleview;
    RecyclerView science_recycleview;
    RecyclerView problem_recycleview;
    NestedScrollView nested_scrolledview;

    ExampleAdapter exampleAdapter;
    private ProblemAdapter problemAdapter;
    private ScienceAdapter scienceAdapter;

    private ArrayList<ExampleData> mExampleDatas = new ArrayList<>();
    private ArrayList<ScienceData> mScienceDatas = new ArrayList<>();
    private ArrayList<ProblemData> mProblemDatas = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       // return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_nested_scroll,container,false);
        initView(view);
        return view ;
    }

    @Override
    protected void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);
        initData();
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

    private void initView(View view){
        nested_scrolledview = (NestedScrollView)view.findViewById(R.id.nested_scrolledview);

        example_recycleview = (RecyclerView)view.findViewById(R.id.example_recycleview);
        science_recycleview = (RecyclerView)view.findViewById(R.id.science_recycleview);
        problem_recycleview = (RecyclerView)view.findViewById(R.id.problem_recycleview);

        LinearLayoutManager mGlobalLLmanager = new LinearLayoutManager(_mActivity, LinearLayoutManager.VERTICAL, false);
        example_recycleview.setLayoutManager(mGlobalLLmanager);

        exampleAdapter = new ExampleAdapter(example_recycleview);
        example_recycleview.setAdapter(exampleAdapter.getHeaderAndFooterAdapter());

        exampleAdapter.setOnRVItemClickListener(new BGAOnRVItemClickListener() {
            @Override
            public void onRVItemClick(ViewGroup parent, View itemView, int position) {
                Toast.makeText(_mActivity, exampleAdapter.getData().get(position).getContent(),Toast.LENGTH_SHORT).show();
            }
        });

        problemAdapter = new ProblemAdapter(problem_recycleview);
        scienceAdapter = new ScienceAdapter(science_recycleview);
        LinearLayoutManager mGlobalLLmanager01 = new LinearLayoutManager(_mActivity, LinearLayoutManager.VERTICAL, false);
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

        LinearLayoutManager mGlobalLLmanager02 = new LinearLayoutManager(_mActivity, LinearLayoutManager.HORIZONTAL, false);
        problem_recycleview.setLayoutManager(mGlobalLLmanager02);

        science_recycleview.setAdapter(scienceAdapter);
        problem_recycleview.setAdapter(problemAdapter);

        problemAdapter.setOnRVItemClickListener(new BGAOnRVItemClickListener() {
            @Override
            public void onRVItemClick(ViewGroup parent, View itemView, int position) {
                Toast.makeText(_mActivity, problemAdapter.getData().get(position).getContent()+ position,Toast.LENGTH_SHORT).show();
            }
        });

        scienceAdapter.setOnItemChildClickListener(new BGAOnItemChildClickListener() {
            @Override
            public void onItemChildClick(ViewGroup parent, View childView, int position) {
                int id = childView.getId();
                //Log.v("TAG","kevin childView="+childView);
                switch (id){
                    case R.id.tv_example:
                        Toast.makeText(_mActivity, scienceAdapter.getData().get(position).getContent(),Toast.LENGTH_SHORT).show();
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


        twinklingRefreshLayout = (TwinklingRefreshLayout)view.findViewById(R.id.refresh_layout);
        ProgressLayout headerView = new ProgressLayout(_mActivity);
        twinklingRefreshLayout.setHeaderView(headerView);
        twinklingRefreshLayout.setFloatRefresh(true);
        twinklingRefreshLayout.setEnableOverScroll(false);
        twinklingRefreshLayout.setHeaderHeight(100);
        twinklingRefreshLayout.setMaxHeadHeight(120);
        twinklingRefreshLayout.setBottomHeight(40);
        twinklingRefreshLayout.setMaxBottomHeight(80);
        twinklingRefreshLayout.setTargetView(nested_scrolledview);
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


}
