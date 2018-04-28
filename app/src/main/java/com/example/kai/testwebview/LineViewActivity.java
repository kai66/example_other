package com.example.kai.testwebview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import widget.SimpleLineView;

/**
 * Created by kai on 2018/2/26.
 */

public class LineViewActivity  extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lineview);
        SimpleLineView mSimpleLineChart = (SimpleLineView) findViewById(R.id.simpleLineView);
        String[] xItem = {"1月","2月","3月","4月","5月","6月","7月","8月","9月","10月","11月","12月"};
        String[] yItem = {"0","1000","2000","3000","4000"};
        String[] data ={"0","1100","2200","3000","3030","3998","2000","1050","600","0","2500","3500"};
        mSimpleLineChart.setXLabels(xItem);
        mSimpleLineChart.setYLabels(yItem);
        mSimpleLineChart.setData(data);
    }

}
