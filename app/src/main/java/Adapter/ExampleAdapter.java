package Adapter;

import android.support.v7.widget.RecyclerView;

import com.example.kai.testwebview.R;

import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;
import entity.ExampleData;

/**
 * Created by kai on 2018/3/12.
 */

public class ExampleAdapter extends BGARecyclerViewAdapter<ExampleData> {

    public ExampleAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_example);
    }

    @Override
    protected void fillData(BGAViewHolderHelper helper, int position, ExampleData model) {
        helper.setText(R.id.tv_example,model.getContent()+ "-"+position);
    }

}
