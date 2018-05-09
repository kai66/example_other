package com.example.kai.testwebview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by kai on 2018/5/9.
 */

public class TabTestFragment extends SupportFragment{

    public static TabTestFragment newInstance(){
        TabTestFragment tabTestFragment = new TabTestFragment();
        return tabTestFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_tabtest,container,false);
        return view;
    }
}
