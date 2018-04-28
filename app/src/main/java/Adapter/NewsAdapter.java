package Adapter;

import android.support.v7.widget.RecyclerView;

import com.example.kai.testwebview.R;

import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;
import entity.NewsResultEntity;

/**
 * Created by kai on 2018/4/16.
 */

public class NewsAdapter extends BGARecyclerViewAdapter<NewsResultEntity> {

    public NewsAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_news);
    }

    @Override
    protected void fillData(BGAViewHolderHelper helper, int position, NewsResultEntity model) {
        helper.setText(R.id.type, model.getType() );
        helper.setText(R.id.who,model.getDesc());
    }
}
