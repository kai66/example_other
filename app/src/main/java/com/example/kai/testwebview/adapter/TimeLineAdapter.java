package com.example.kai.testwebview.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kai.testwebview.R;
import com.github.vipulasri.timelineview.TimelineView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Util.AnimUtil;
import entity.ExampleData;

/**
 * Created by kai on 2018/5/8.
 */

public class TimeLineAdapter extends RecyclerView.Adapter<TimeLineAdapter.TimeLineHolder>{

    private Context context;
    private List<ExampleData> list;

    private List<Integer> intList = new ArrayList<Integer>();

    private HashMap<String,String> hashMap = new HashMap<String, String>();

    public TimeLineAdapter(Context context, List<ExampleData> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public TimeLineHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_test_item,parent,false)
        View view =  LayoutInflater.from(context).inflate(R.layout.item_time_line, parent, false);
        TimeLineHolder holder = new TimeLineHolder(view,viewType);
        return holder;
    }

    @Override
    public int getItemViewType(int position) {
        return TimelineView.getTimeLineViewType(position,getItemCount());
    }

    @Override
    public void onBindViewHolder(TimeLineHolder holder, int position) {
        holder.tv_title.setText(this.list.get(position).getTitle());
        holder.tv_content.setText(this.list.get(position).getContent());
        //holder.timelineView.setMarker(new ColorDrawable(context.getResources().getColor(R.color.orange)));
        holder.timelineView.setMarker(context.getDrawable(R.drawable.round_back));
        holder.tv_time.setText("test--"+position);
        holder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AnimUtil.newInstance(context, holder.hiddenlayout,holder.img,100).toggle();
                /*
                int linecount =  holder.tv_content.getLineCount();
                if(holder.tv_content.getVisibility() ==  View.GONE ){
                    holder.tv_content.setVisibility(View.VISIBLE);
                }else{
                    holder.tv_content.setVisibility(View.GONE);
                }
                */
            }
        });

    }

    @Override
    public int getItemCount() {
        return  this.list == null ? 0: this.list.size();
    }

    class TimeLineHolder extends RecyclerView.ViewHolder{

          TextView tv_title;
          TextView tv_content;
          TimelineView timelineView;
          TextView tv_time;
         // LinearLayout detail_content;
          CardView cardview;
          ImageView img;
          LinearLayout hiddenlayout;

        public TimeLineHolder(View itemView,int viewType) {
            super(itemView);
            tv_title = (TextView)itemView.findViewById(R.id.title);
            tv_content = (TextView)itemView.findViewById(R.id.content);
            timelineView = (TimelineView)itemView.findViewById(R.id.time_marker);
            tv_time = (TextView)itemView.findViewById(R.id.time);
            //detail_content = (LinearLayout)itemView.findViewById(R.id.detail_content);
            cardview = (CardView)itemView.findViewById(R.id.cardview);
            img = (ImageView)itemView.findViewById(R.id.img);
            hiddenlayout = (LinearLayout)itemView.findViewById(R.id.hiddenlayout);
            timelineView.initLine(viewType);
        }
    }

}
