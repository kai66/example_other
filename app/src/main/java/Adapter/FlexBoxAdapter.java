package Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kai.testwebview.FlexboxLayoutActivity;
import com.example.kai.testwebview.R;
import com.example.kai.testwebview.adapter.TimeLineAdapter;
import com.github.vipulasri.timelineview.TimelineView;

import java.util.List;

import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;

/**
 * Created by kai on 2018/5/9.
 */

public class FlexBoxAdapter extends RecyclerView.Adapter<FlexBoxAdapter.ViewHolder>{

    List<String> list;
    private Context context;

    public FlexBoxAdapter(Context context, List<String> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public FlexBoxAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(context).inflate(R.layout.item_flexbox, null, false);
        FlexBoxAdapter.ViewHolder holder = new FlexBoxAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(FlexBoxAdapter.ViewHolder holder, int position) {
        holder.tv_title.setText(list.get(position));
        holder.tv_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,list.get(position),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_title;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_title = (TextView)itemView.findViewById(R.id.tv_name);
        }
    }


}
