package Util;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by kai on 2018/7/3.
 */

public class SpaceItemDecoration extends RecyclerView.ItemDecoration{

   private  int hor;
   private  int ver;

   public  SpaceItemDecoration(int hor,int ver){
       this.hor = hor;
       this.ver = ver;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        //outRect就是你那个item条目的矩形
        outRect.left = hor;  //相当于 设置 left padding
        outRect.top = ver;   //相当于 设置 top padding
        //outRect.right = padding; //相当于 设置 right padding
        //outRect.bottom = padding;  //相当于 设置 bottom padding
    }

}
