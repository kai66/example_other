package com.example.kai.testwebview.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kai.testwebview.R;

import java.util.ArrayList;

import Adapter.MutilBaseAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;
import entity.MutilModel;

/**
 * Created by kai on 2018/6/11.
 */

public class MutilLayoutFragment extends  BaseFragment{

    RecyclerView recyclerView;
    ArrayList<MutilModel> mutilModels;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_mutil_layout,container,false);
        recyclerView = (RecyclerView)view.findViewById(R.id.recycle_view);
        return view;
    }

    MutilBaseAdapter<MutilModel> mutilBaseAdapter;

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(_mActivity, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        mutilModels = new ArrayList<>();
        for(int i = 0;i<4;i++){
            MutilModel mutilModel = new MutilModel();
            mutilModel.setName("name "+i);
            ArrayList<String> list =  new ArrayList<>();
            for(int j=0;j<i;j++){
                list.add("list "+j);
            }
            mutilModel.setList(list);
            mutilModels.add(mutilModel);
        }
        mutilBaseAdapter = new MutilBaseAdapter<MutilModel>(recyclerView) {
            @Override
            public void fillMultiItemData(BGAViewHolderHelper helper, int position, MutilModel model) {
                 if(model.getList().size() == 0){
                     helper.setText(R.id.tv_one,model.getName());
                 }else if((model.getList().size() == 3)){
                     helper.setText(R.id.tv_two,model.getName());
                 }else{
                     helper.setText(R.id.tv_three,model.getName());
                 }
            }

            @Override
            public int getMultiItemViewType(int position) {
                int size = mutilBaseAdapter.getData().get(position).getList().size();
                if (size == 0) {
                    return R.layout.item_mutil_one;
                } else if (size == 1) {
                    return R.layout.item_mutil_three;
                } else if (size == 2) {
                    return R.layout.item_mutil_three;
                } else {
                    return R.layout.item_mutil_two;
                }
            }
        };
        mutilBaseAdapter.setData(mutilModels);
        recyclerView.setAdapter(mutilBaseAdapter);

    }





}
