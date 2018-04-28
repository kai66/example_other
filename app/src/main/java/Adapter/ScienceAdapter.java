package Adapter;

import android.support.v7.widget.RecyclerView;

import com.example.kai.testwebview.R;

import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;
import entity.ScienceData;

/**
 * Created by kai on 2018/3/12.
 */

public class ScienceAdapter extends BGARecyclerViewAdapter<ScienceData> {

    public ScienceAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_science_example);
    }

    @Override
    protected void fillData(BGAViewHolderHelper helper, int position, ScienceData model) {
        helper.setText(R.id.tv_example, model.getContent());
        helper.setItemChildClickListener(R.id.tv_example);
        helper.setItemChildClickListener(R.id.bt_delete);
    }

}
