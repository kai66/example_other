package com.example.kai.testwebview.adapter;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.net.LinkAddress;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kai.testwebview.R;
import com.github.vipulasri.timelineview.TimelineView;

import java.util.List;

import entity.ExampleData;

/**
 * Created by kai on 2018/5/8.
 */

public class TimeLineAdapter extends RecyclerView.Adapter<TimeLineAdapter.TimeLineHolder>{

    private Context context;
    private List<ExampleData> list;

    public TimeLineAdapter(Context context, List<ExampleData> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public TimeLineHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(context).inflate(R.layout.item_time_line, null, false);
        TimeLineHolder holder = new TimeLineHolder(view,viewType);
        return holder;
    }


    @Override
    public void onBindViewHolder(TimeLineHolder holder, int position) {
        holder.tv_title.setText(this.list.get(position).getTitle());
        holder.tv_content.setText(this.list.get(position).getContent());
        holder.timelineView.setMarker(new ColorDrawable(context.getResources().getColor(R.color.orange)));
    }

    @Override
    public int getItemCount() {
        return  this.list == null ? 0: this.list.size();
    }

    class TimeLineHolder extends RecyclerView.ViewHolder{

          TextView tv_title;
          TextView tv_content;
          TimelineView timelineView;

        public TimeLineHolder(View itemView,int viewType) {
            super(itemView);
            tv_title = (TextView)itemView.findViewById(R.id.title);
            tv_content = (TextView)itemView.findViewById(R.id.content);
            timelineView = (TimelineView)itemView.findViewById(R.id.timeline_view);
        }
    }

}
