package Adapter;

import android.support.v7.widget.RecyclerView;

import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;

/**
 * Created by kai on 2018/6/11.
 */

public abstract class MutilBaseAdapter<T> extends BGARecyclerViewAdapter<T> {


    public MutilBaseAdapter(RecyclerView recyclerView) {
        super(recyclerView);
    }

    @Override
    public int getItemViewType(int position) {
        return getMultiItemViewType(position);
    }

    @Override
    protected void fillData(BGAViewHolderHelper helper, int position, T model) {
        fillMultiItemData(helper, position, model);
    }

    public abstract void fillMultiItemData(BGAViewHolderHelper helper, int position, T model);

    public abstract int getMultiItemViewType(int position);


}
