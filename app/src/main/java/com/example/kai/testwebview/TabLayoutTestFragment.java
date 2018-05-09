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

public class TabLayoutTestFragment extends SupportFragment{

    public static TabLayoutTestFragment newInstance(){
        TabLayoutTestFragment tabLayoutTestFragment = new TabLayoutTestFragment();
        return tabLayoutTestFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tablayout_test,container,false);
    }
}
