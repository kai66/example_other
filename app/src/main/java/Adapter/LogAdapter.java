package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.kai.testwebview.R;

import java.util.List;

import entity.LogModel;

/**
 * Created by kai on 2018/5/10.
 */

public class LogAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<LogModel> logModels;
    private Context context;

    public LogAdapter(Context context, List<LogModel> itemModels) {
        inflater = LayoutInflater.from(context);
        this.logModels = itemModels;
        this.context = context;
    }

    @Override
    public int getCount() {
        return logModels.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LogModel itemModel = logModels.get(position);
        ViewHolder holder = null;
        View relativeView = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.lv_item, null);
            holder.tVprojectTitle = (TextView) convertView.findViewById(R.id.tv_projectcontent_title);
            holder.tVprojectContent = (TextView) convertView.findViewById(R.id.tv_projectcontent_content);
            holder.tVprojectName = (TextView) convertView.findViewById(R.id.tv_projectname_content);
            holder.tVprojectStage = (TextView) convertView.findViewById(R.id.tv_projectstage_content);
            holder.linearLayout = (LinearLayout) convertView.findViewById(R.id.timeline_item_top);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();
        relativeView = (RelativeLayout) convertView.findViewById(R.id.timeline_item_bottom);
        relativeView.measure(0, 0);
        if (itemModel.isVisiable()) {
            holder.linearLayout.setBackgroundResource(R.drawable.back_white_corner);
            holder.tVprojectTitle.setTextColor(context.getResources().getColor(R.color.white));
            holder.tVprojectContent.setTextColor(context.getResources().getColor(R.color.white));
            ((LinearLayout.LayoutParams) relativeView.getLayoutParams()).bottomMargin = 0;
            relativeView.setVisibility(View.VISIBLE);
        } else {
            holder.linearLayout.setBackgroundResource(R.drawable.back_white_corner);
            holder.tVprojectContent.setTextColor(context.getResources().getColor(R.color.orange));
            ((LinearLayout.LayoutParams) relativeView.getLayoutParams()).bottomMargin = (-1) * relativeView.getMeasuredHeight();
            relativeView.setVisibility(View.GONE);
        }
        holder.tVprojectContent.setText(itemModel.getProjectContent());
        holder.tVprojectName.setText(itemModel.getProjectName());
        holder.tVprojectStage.setText(itemModel.getProjectStage());
        return convertView;
    }
    public void refuseAdapter(List<LogModel> datas) {
        this.logModels = datas;
        this.notifyDataSetChanged();
    }

    class ViewHolder {
        TextView tVprojectContent, tVprojectName, tVprojectStage, tVprojectTitle;
        LinearLayout linearLayout;
    }

}
