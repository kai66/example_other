package com.example.kai.testwebview.fragment;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.example.kai.testwebview.R;
import com.example.kai.testwebview.adapter.JoneBaseAdapter;

import java.util.ArrayList;

import Adapter.ExampleAdapter;
import Util.DensityUtil;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;
import entity.ExampleData;

/**
 * Created by kai on 2018/6/14.
 */

public class TextDrawableFragment extends BaseFragment{

    RecyclerView recyclerView ;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_text_drawable,container,false);
        recyclerView =  (RecyclerView) view.findViewById(R.id.recycle_view);
        return view ;
    }
    JoneBaseAdapter<ExampleData> joneBaseAdapter;

    void initView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(_mActivity,LinearLayoutManager.VERTICAL,false);
        joneBaseAdapter = new JoneBaseAdapter<ExampleData>(recyclerView,R.layout.item_my_question) {
            @Override
            public void fillItemData(BGAViewHolderHelper helper, int position, ExampleData model) {
                helper.setText(R.id.tv_content,model.getContent());
                ColorGenerator generator = ColorGenerator.MATERIAL;
                int color2 = generator.getColor(model.getTitle());
                int fontsize = DensityUtil.sp2px(_mActivity,14);
                int radius  = DensityUtil.dip2px(_mActivity,3);
                TextDrawable drawable = TextDrawable.builder()
                        .beginConfig()
                        .textColor(Color.BLACK)
                        .useFont(Typeface.DEFAULT)
                        .fontSize(fontsize) /* size in px */
                        .withBorder(1)
                        .endConfig()
                        .buildRoundRect(model.getTitle(), color2,radius);

                helper.setText(R.id.tv_title,model.getTitle());
                Rect bounds = new Rect();
                TextPaint paint;
                paint = helper.getTextView(R.id.tv_title).getPaint();
                paint.getTextBounds(model.getTitle(), 0,  model.getTitle().length(), bounds);
                int pswidth = bounds.width();
                int psheight = bounds.height();
                int plus = DensityUtil.dip2px(_mActivity,8);
                drawable.setBounds(0,0,pswidth+plus,psheight+plus);
                helper.getTextView(R.id.tv_content).setCompoundDrawablePadding(plus);
                helper.getTextView(R.id.tv_content).setCompoundDrawables(null,null,drawable,null);
                //helper.getTextView(R.id.tv_content).setCompoundDrawablePadding(plus);
                //helper.getTextView(R.id.tv_content).setCompoundDrawablesWithIntrinsicBounds(null,null,drawable,null);
            }
        };

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(joneBaseAdapter);

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        initView();
        initData();
    }

    ArrayList<ExampleData>  exampleDataArrayList = new ArrayList<>();
    private void initData(){

        ExampleData data01 = new ExampleData();
        data01.setContent("data01");
        data01.setTitle("test01");
        exampleDataArrayList.add(data01);

        ExampleData data02 = new ExampleData();
        data02.setContent("data02data02data02data02data02data02data02data02data02data02data02data02data02data02data02data" +
                "02vdata02data02data02data02data02data02data02data02data02data02data02data02data02data02data02data02v");
        data02.setTitle("test02hhklgihuipup");
        exampleDataArrayList.add(data02);

        ExampleData data03 = new ExampleData();
        data03.setContent("data03");
        data03.setTitle("test02");
        exampleDataArrayList.add(data03);

        ExampleData data04 = new ExampleData();
        data04.setContent("data03000kjhk");
        data04.setTitle("test01");
        exampleDataArrayList.add(data04);


        joneBaseAdapter.setData(exampleDataArrayList);

    }



}
