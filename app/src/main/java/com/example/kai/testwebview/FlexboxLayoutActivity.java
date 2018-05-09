package com.example.kai.testwebview;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayout;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import java.util.ArrayList;

import Adapter.FlexBoxAdapter;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * Created by kai on 2018/5/9.
 */

public class FlexboxLayoutActivity extends SupportActivity{

    FlexboxLayout flexboxlayout;
    ArrayList<String> lists = new ArrayList<>();
    RecyclerView recyclerview;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flexbox_layout);
        initView();
    }

    private void initView(){
        recyclerview = (RecyclerView)findViewById(R.id.recyclerview);
        flexboxlayout = (FlexboxLayout)findViewById(R.id.flexboxlayout);
        lists.add("test01test01test000test01test000");
        lists.add("test01test000");
        lists.add("test01yyyyyyy");
        lists.add("test01u");
        lists.add("test01hhhtest01ujj");
        lists.add("test01llll");
        lists.add("test01eeee");
        lists.add("test01ccccc");
        lists.add("hhhh");
        lists.add("hhh");
        lists.add("hhhh");
        lists.add("test01ccccctest01ccccc");
        for(int i = 0;i<lists.size();i++){
            flexboxlayout.addView( createNewFlexItemTextView(lists.get(i)));
        }

        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(FlexboxLayoutActivity.this);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.FLEX_START);
        layoutManager.setFlexWrap(FlexWrap.WRAP);
        recyclerview.setLayoutManager(layoutManager);

        FlexBoxAdapter flexBoxAdapter = new FlexBoxAdapter(FlexboxLayoutActivity.this,lists);
        recyclerview.setAdapter(flexBoxAdapter);

    }

    /**
     * 动态创建TextView
     * @param book
     * @return
     */
    private TextView createNewFlexItemTextView(final String book) {
        TextView textView = new TextView(this);
        textView.setGravity(Gravity.CENTER);
        textView.setText(book);
        textView.setTextSize(18);
        textView.setTextColor(getResources().getColor(R.color.colorAccent));
        textView.setBackgroundResource(R.drawable.back_white_corner);
        //textView.setTag(book.getId());
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(FlexboxLayoutActivity.this,book,Toast.LENGTH_SHORT).show();
            }
        });
        int padding = dpToPixel(this, 4);
        int paddingLeftAndRight = dpToPixel(this, 8);
        ViewCompat.setPaddingRelative(textView, paddingLeftAndRight, padding, paddingLeftAndRight, padding);
        FlexboxLayout.LayoutParams layoutParams = new FlexboxLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        int margin = dpToPixel(this, 6);
        int marginTop = dpToPixel(this, 16);
        layoutParams.setMargins(margin, marginTop, margin, 0);
        textView.setLayoutParams(layoutParams);
        return textView;
    }


        public  int pixelToDp(Context context, int pixel) {
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            return pixel < 0 ? pixel : Math.round(pixel / displayMetrics.density);
        }
        public  int dpToPixel(Context context, int dp) {
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            return dp < 0 ? dp : Math.round(dp * displayMetrics.density);
        }




}
