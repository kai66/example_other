package com.example.kai.testwebview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import widget.RelativeLayoutTextView;

/**
 * Created by kai on 2018/5/10.
 */

public class RlTextViewActivity extends AppCompatActivity{


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rltextview);
        RelativeLayoutTextView customTextView1 = (RelativeLayoutTextView) findViewById(R.id.coustomTextView);
        /*
        customTextView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RlTextViewActivity.this,"test",Toast.LENGTH_SHORT).show();
            }
        });
        */

       customTextView1.setLeftImg(getResources().getDrawable(R.drawable.ic_search))
                .setRightImg(getResources().getDrawable(R.drawable.icon_portrait))
                .setLeftTv("代码添加","#c95fdc")
                .setRightTv("代码添加","#4be1c3")
                .setCenterTv("代码",null)
                .setLeftTopTv("上",null)
                .setLeftBottomTv("下",null)
                .setBottomLine("#1587e7")
                .setCustomTvBackground("#4dacff");

        customTextView1.setOnTextViewClickListener(new RelativeLayoutTextView.OnTextViewClickListener(){
            @Override
            public void OnLeftImgClick() {
                Toast.makeText(RlTextViewActivity.this,"左边图片",Toast.LENGTH_SHORT).show();
            }
            @Override
            public void OnRightImgClick() {
                Toast.makeText(RlTextViewActivity.this,"右边图片",Toast.LENGTH_SHORT).show();
            }
            @Override
            public void OnTextViewClick() {
                Toast.makeText(RlTextViewActivity.this,"布局",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
