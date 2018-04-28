package com.example.kai.testwebview.mvptest;

import base.IView;
import entity.NewsEntity;

/**
 * Created by kai on 2018/4/19.
 */

public class MvpTestContract {

    public interface IMvpTestView extends IView{
        void refreshDetailInfo(NewsEntity entity);
    }


    public interface IMvpTestPresenter{
        void getDetailInfo();
    }




}
