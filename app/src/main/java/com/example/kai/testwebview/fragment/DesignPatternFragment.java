package com.example.kai.testwebview.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.kai.testwebview.R;

/**
 * Created by kai on 2018/5/26.
 */

public class DesignPatternFragment extends BaseFragment{



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view =  inflater.inflate(R.layout.fragment_design_pattern,container,false);
       return view ;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
