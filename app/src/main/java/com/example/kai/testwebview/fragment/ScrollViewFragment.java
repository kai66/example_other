package com.example.kai.testwebview.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.kai.testwebview.R;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by kai on 2018/6/4.
 */

public class ScrollViewFragment extends BaseFragment{

    TextView tv_01;
    TextView tv_02;

    Button bt_01;
    Button bt_02;
    Button bt_03;

    ScrollView scroll_view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view =  inflater.inflate(R.layout.fragment_scroll_view,container,false);
       initView(view);
        return view;
    }

    private void initView(View view){
        scroll_view = (ScrollView)view.findViewById(R.id.scroll_view);

        tv_01 = (TextView)view.findViewById(R.id.tv_01);
        tv_02 = (TextView)view.findViewById(R.id.tv_02);

        bt_01 = (Button) view.findViewById(R.id.bt_01);
        bt_02 = (Button)view.findViewById(R.id.bt_02);
        bt_03 = (Button)view.findViewById(R.id.bt_03);

        bt_01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               int top =  tv_01.getTop();
               scroll_view.smoothScrollTo(0,top);
            }
        });

        bt_03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int top =  tv_02.getTop();
                scroll_view.smoothScrollTo(0,top);
            }
        });

        bt_02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int top =  bt_01.getTop();
                //scroll_view.scrollTo(0,top);
                scroll_view.smoothScrollTo(0,top);
            }
        });

    }



}
