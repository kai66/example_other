package com.example.kai.testwebview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import Adapter.LogAdapter;
import Util.ExpandAnimation;
import entity.LogModel;

/**
 * Created by kai on 2018/5/10.
 */

public class ExpandActivity extends AppCompatActivity{

    private ListView listView;
    private LogAdapter adapter;
    /** 控制重复点击 */
    private boolean animationFinished = true;
    private List<LogModel> logModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expand);
        listView = (ListView) findViewById(R.id.listview);
        getDatas();
        adapter = new LogAdapter(this, logModels);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!animationFinished)
                    return;
                View animationView = view.findViewById(R.id.timeline_item_bottom);
                ExpandAnimation animation = new ExpandAnimation(animationView, 300);
                animation.setAnimationListener(new MyAnimationListener(position));
                animationView.startAnimation(animation);
            }
        });
    }

    private void getDatas() {
        logModels = new ArrayList<LogModel>();
        for (int i = 0; i < 5; i++) {
            LogModel logModel = new LogModel("测试" + i, "大数据项目" + i, "第 " + i + " 阶段");
            logModels.add(logModel);
        }

        LogModel logModel01 = new LogModel("测试" + 001, "大数据项目大数据项目大数据项目大数据项目大数据项目大数据项目" +
                "大数据项目大数据项目大数据项目大数据项目大数据项目大数据项目大数据项目大数据项目大数据项目大数据项目"
                + 002, "第 " + 001 + " 阶段");
        logModels.add(logModel01);


    }

    class MyAnimationListener implements Animation.AnimationListener {

        private int position;
        private LinearLayout linearLayout;
        private TextView tVprojectTitle, tVprojectContent;

        public MyAnimationListener(int position) {
            this.position = position;
            init();
        }

        private void init() {
            View view = listView.getChildAt(position);
            linearLayout = (LinearLayout) view.findViewById(R.id.timeline_item_top);
            tVprojectTitle = (TextView) view.findViewById(R.id.tv_projectcontent_title);
            tVprojectContent = (TextView) view.findViewById(R.id.tv_projectcontent_content);
        }

        @Override
        public void onAnimationStart(Animation arg0) {
            animationFinished = false;
            LogModel item = logModels.get(position);
            if (!item.isVisiable()) {
                linearLayout.setBackgroundResource(R.color.Blue);
                tVprojectTitle.setTextColor(getResources().getColor(R.color.white));
                tVprojectContent.setTextColor(getResources().getColor(R.color.white));
            }
        }

        @Override
        public void onAnimationRepeat(Animation arg0) {

        }

        @Override
        public void onAnimationEnd(Animation arg0) {
            LogModel item = logModels.get(position);
            item.setVisiable(!item.isVisiable());
            adapter.notifyDataSetChanged();
            animationFinished = true;
        }
    }

}
