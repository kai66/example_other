package com.example.kai.testwebview.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kai.testwebview.R;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import java.util.ArrayList;

/**
 * Created by kai on 2018/6/12.
 */

public class SmartBarFragment extends BaseFragment{

    ViewPager mViewpager;
    SmartTabLayout tablelayout;
    ArrayList<Fragment> fragments = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_smartbar,container,false);
        initView(view);
        return view ;
    }

    void initView(View view){
        tablelayout = (SmartTabLayout)view.findViewById(R.id.smarttablayout);
        mViewpager = (ViewPager) view.findViewById(R.id.viewpager);
        FragmentPagerItems pages = new FragmentPagerItems(_mActivity);
        for (int i=0;i<20;i++) {
            pages.add(FragmentPagerItem.of("TAG"+i*i, DetailInfoFragment.class));
        }
        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(getFragmentManager(),pages);
        mViewpager.setAdapter(adapter);
        tablelayout.setViewPager(mViewpager);
    }

}
