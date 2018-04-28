package Adapter;

import android.support.v7.widget.RecyclerView;

import com.example.kai.testwebview.R;

import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;
import entity.ExampleData;
import entity.ProblemData;

/**
 * Created by kai on 2018/3/12.
 */

public class ProblemAdapter extends BGARecyclerViewAdapter<ProblemData> {

    public ProblemAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_example);
    }

    @Override
    protected void fillData(BGAViewHolderHelper helper, int position, ProblemData model) {
        helper.setText(R.id.tv_example,model.getContent()+ "-"+position);
    }
}
