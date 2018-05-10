package com.example.kai.testwebview;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.kai.testwebview.adapter.TabPagerAdapter;
import com.example.kai.testwebview.adapter.TimeLineAdapter;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.vipulasri.timelineview.TimelineView;

import java.util.ArrayList;
import java.util.List;

import entity.ExampleData;
import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by kai on 2018/3/13.
 */

public class PieChartActivity extends SupportActivity {

    PieChart mPieChart;
    RecyclerView recycle_view;

    TabLayout tabLayout;

    ViewPager viewPager;
    TabLayout.Tab tab01;
    TabLayout.Tab tab02;

    String[] titles = new String[10];
    SupportFragment[] supportFragments = new SupportFragment[10];
    TabPagerAdapter tabPagerAdapter;

    private int prePosition = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_piechart);
        initPieChatView();
        initTimeLineView();
        initTabLayout();
    }

    private void initTabLayout(){
        viewPager = (ViewPager)findViewById(R.id.viewpager);
        tabLayout = (TabLayout)findViewById(R.id.tab_layout);
        tab01 = tabLayout.newTab();
        tab01.setText("tab01");
        tab02 = tabLayout.newTab();
        tab02.setText("tab02");
        tabLayout.addTab(tab01);
        tabLayout.addTab(tab02);

        titles[0]= "tab01";
        titles[1]= "tab02";

        titles[2]= "tab01";
        titles[3]= "tab02";

        titles[4]= "tab01";
        titles[5]= "tab02";

        titles[6]= "tab01";
        titles[7]= "tab02";

        titles[8]= "tab01";
        titles[9]= "tab02";

        supportFragments[0] =  TabTestFragment.newInstance();
        supportFragments[1] = TabLayoutTestFragment.newInstance();

        supportFragments[2] =  TabTestFragment.newInstance();
        supportFragments[3] = TabLayoutTestFragment.newInstance();

        supportFragments[4] =  TabTestFragment.newInstance();
        supportFragments[5] = TabLayoutTestFragment.newInstance();

        supportFragments[6] =  TabTestFragment.newInstance();
        supportFragments[7] = TabLayoutTestFragment.newInstance();

        supportFragments[8] =  TabTestFragment.newInstance();
        supportFragments[9] = TabLayoutTestFragment.newInstance();

        tabPagerAdapter = new TabPagerAdapter(getSupportFragmentManager(),supportFragments,titles);
        viewPager.setAdapter(tabPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setCurrentItem(0);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition(),true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                prePosition = tab.getPosition();
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void initTimeLineView(){

        ArrayList<ExampleData> data = new ArrayList<>();

        for (int i =0 ;i<2;i++){
            ExampleData exampleData = new ExampleData();
            exampleData.setTitle("test"+i);
            exampleData.setContent("content"+i);
            data.add(exampleData);
        }

        ExampleData exampleData01 = new ExampleData();
        exampleData01.setTitle("test");
        exampleData01.setContent("contentcontentcontentcontentcontentcontentcontentcontentcontentcontentcontentcontent");
        data.add(exampleData01);

        for (int i =0 ;i<2;i++){
            ExampleData exampleData = new ExampleData();
            exampleData.setTitle("test"+i);
            exampleData.setContent("content"+i);
            data.add(exampleData);
        }



        recycle_view = (RecyclerView)findViewById(R.id.recycle_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycle_view.setLayoutManager(linearLayoutManager);
        TimeLineAdapter timeLineAdapter =  new TimeLineAdapter(PieChartActivity.this,data);
        recycle_view.setAdapter(timeLineAdapter);
        timeLineAdapter.notifyDataSetChanged();
    }

    private void initPieChatView(){
        mPieChart = (PieChart)findViewById(R.id.chartview);
        List<Integer> colors = new ArrayList<>();
        colors.add(Color.BLACK);
        colors.add(Color.GRAY);
        colors.add(Color.RED);
        colors.add(Color.GREEN);
        List<PieEntry> pieEntries = new ArrayList<>();
        pieEntries.add(new PieEntry(1, "11"));
        pieEntries.add(new PieEntry(2, "22"));
        pieEntries.add(new PieEntry(3, "33"));
        PieDataSet iPieDataSet = new PieDataSet(pieEntries, "");
        iPieDataSet.setColors(colors);
        iPieDataSet.setValueTextColors(colors);
        iPieDataSet.setSliceSpace(0);   // 每块之间的距离
        PieData pieData = new PieData(iPieDataSet);
        mPieChart.setData(pieData);
        /*mPieChart.setDrawSliceText(true);*/   // : 将X值绘制到饼状图环切片内,否则不显示。默认true,已弃用，用下面setDrawEntryLabels()
        mPieChart.setDrawEntryLabels(true);   // 同上,默认true，记住颜色和环不要一样，否则会显示不出来
        mPieChart.setUsePercentValues(true);    // 表内数据用百分比替代，而不是原先的值。并且ValueFormatter中提供的值也是该百分比的。默认false
        //mPieChart.setCenterText("asc"); // 圆环中心的文字，会自动适配不会被覆盖
        mPieChart.setCenterTextRadiusPercent(0f); // 中心文本边界框矩形半径比例，默认是100%.
        mPieChart.setHoleRadius(0);  // 设置中心圆半径占整个饼形图圆半径（图表半径）的百分比。默认50%
        mPieChart.setTransparentCircleRadius(0);   // 设置环形与中心圆之间的透明圆环半径占图表半径的百分比。默认55%（比如，中心圆为50%占比，而透明环设置为55%占比，要去掉中心圆的占比，也就是环只有5%的占比）
       // mPieChart.setTransparentCircleColor(Color.RED); // 上述透明圆环的颜色
       // mPieChart.setTransparentCircleAlpha(50);    // 上述透明圆环的透明度[0-255]，默认100
        mPieChart.setMaxAngle(360);    // 设置整个饼形图的角度，默认是360°即一个整圆，也可以设置为弧，这样现实的值也会重新计算

        mPieChart.setClickable(false);
        mPieChart.setTouchEnabled(false);

        mPieChart.setDrawMarkers(false);
        Description description = new Description();
        description.setText("");
        mPieChart.setDescription(description);

    }



}
