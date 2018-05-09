package com.example.kai.testwebview.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.example.kai.testwebview.TabLayoutTestFragment;
import com.example.kai.testwebview.TabTestFragment;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by kai on 2018/5/9.
 */

public class TabPagerAdapter extends FragmentPagerAdapter{

    private String[] titles ;
    SupportFragment[] fragments;
    public TabPagerAdapter(FragmentManager fm, SupportFragment[] supportFragments,String[] titles) {
        super(fm);
        fragments = supportFragments;
        this.titles = titles;
    }


    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Override
    public int getCount() {
        return this.titles == null ? 0 : this.titles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return this.titles[position];
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }
}
