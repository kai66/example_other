package com.example.kai.testwebview.mvptest;

import base.BaseMVPPresenter;
import entity.NewsEntity;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by kai on 2018/4/19.
 */

public class MvpTestPresenter extends BaseMVPPresenter<MvpTestContract.IMvpTestView> implements MvpTestContract.IMvpTestPresenter{


    public MvpTestPresenter(MvpTestContract.IMvpTestView view) {
        super(view);
    }

    @Override
    public void getDetailInfo() {
        MvpTestRequestManager.getDetail("Android",10,1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<NewsEntity>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(NewsEntity value) {
                        mView.refreshDetailInfo(value);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


}
