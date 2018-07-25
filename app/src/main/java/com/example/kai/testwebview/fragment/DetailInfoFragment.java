package com.example.kai.testwebview.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kai.testwebview.R;
import com.example.kai.testwebview.adapter.JoneBaseAdapter;

import java.util.ArrayList;

import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;
import entity.DetailInfo;

/**
 * Created by kai on 2018/6/11.
 */

public class DetailInfoFragment extends BaseFragment{


    JoneBaseAdapter<DetailInfo> detailInfoJoneBaseAdapter;
    View mHeadView;
    LinearLayout contentLayout;
    RecyclerView recycle_view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_info,container,false);
        initView(view);
        return view;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        initData();
    }

   private void initView(View view){
       recycle_view = (RecyclerView)view.findViewById(R.id.recycle_view);
       mHeadView =  LayoutInflater.from(_mActivity).inflate(R.layout.headview_detailinfo,null);

       ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
       mHeadView.setLayoutParams(lp);

       contentLayout = (LinearLayout) mHeadView.findViewById(R.id.content);
       LinearLayoutManager linearLayoutManager = new LinearLayoutManager(_mActivity,LinearLayoutManager.VERTICAL,false);
       recycle_view.setLayoutManager(linearLayoutManager);
       detailInfoJoneBaseAdapter = new JoneBaseAdapter<DetailInfo>(recycle_view,R.layout.item_question_answer) {
           @Override
           public void fillItemData(BGAViewHolderHelper helper, int position, DetailInfo model) {
               helper.setText(R.id.tv_question,String.valueOf(model.getType()));
               helper.setText(R.id.tv_answer,model.getContent());
           }
       };
       detailInfoJoneBaseAdapter.addHeaderView(mHeadView);
       recycle_view.setAdapter(detailInfoJoneBaseAdapter.getHeaderAndFooterAdapter());
    }

   private void  initData(){
       ArrayList<DetailInfo> detailInfos = new ArrayList<>();
       DetailInfo detailInfo01 = new DetailInfo();
       detailInfo01.setType(1);
       detailInfo01.setContent("test01");
       detailInfos.add(detailInfo01);

       DetailInfo detailInfo02 = new DetailInfo();
       detailInfo02.setType(2);
       detailInfo02.setContent("test02");
       detailInfos.add(detailInfo02);

       DetailInfo detailInfo03 = new DetailInfo();
       detailInfo03.setType(3);
       detailInfo03.setContent("test03");
       detailInfos.add(detailInfo03);

       for(int i=0;i<10;i++){
           detailInfos.add(detailInfo01);
       }

       for (int i=0;i<detailInfos.size();i++){
           if(detailInfos.get(i).getType() == 1){
               contentLayout.addView(createTitleText(detailInfos.get(i).getContent()));
           }else if(detailInfos.get(i).getType() == 2){
               contentLayout.addView(CreateImageView());
           }else if(detailInfos.get(i).getType() == 3){
               contentLayout.addView(createContentText(detailInfos.get(i).getContent()));
           }
       }
       detailInfoJoneBaseAdapter.setData(detailInfos);
    }

    private ImageView CreateImageView(){
        ImageView imageView = new ImageView(_mActivity);
        imageView.setLayoutParams(new LinearLayout.LayoutParams(600, 600));  //设置图片宽高
        //imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));  //设置图片宽高
        imageView.setImageResource(R.color.Blue); //图片资源
        return imageView;
    }

    private TextView createTitleText(String s){
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        TextView textView = new TextView(_mActivity);
        textView.setLayoutParams(params);
        textView.setTextSize(16);
        //textView.setTextColor(getResources().getColor(android.R.color.black));
        textView.setLineSpacing(1.2f, 1.2f);//设置行间距
        textView.setText(s);
        return textView;
    }

    private TextView createContentText(String s){
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
       TextView textView = new TextView(_mActivity);
       textView.setLayoutParams(params);
       textView.setTextSize(13);
       // textView.setTextColor(getResources().getColor(android.R.color.darker_gray));
        textView.setLineSpacing(1.2f, 1.2f);//设置行间距
        textView.setText(s);
       return textView;
    }


}
