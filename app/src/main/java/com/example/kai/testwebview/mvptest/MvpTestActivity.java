package com.example.kai.testwebview.mvptest;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.kai.testwebview.R;

import junit.framework.Assert;

import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import Adapter.NewsAdapter;
import base.BaseMVPActivity;
import entity.NewsEntity;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;

/**
 * Created by kai on 2018/4/19.
 */

public class MvpTestActivity extends BaseMVPActivity<MvpTestPresenter> implements MvpTestContract.IMvpTestView{

    String TAG ="MvpTestActivity";
    RecyclerView recyclerView;
    NewsAdapter mNewsAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
       // mergeDelayError();
        backPressureFlowable();
    }

    private void backPressureFlowable(){
       Flowable.create(new FlowableOnSubscribe<Integer>() {
           @Override
           public void subscribe(FlowableEmitter<Integer> e) throws Exception {
               for(int i =0;i<10;i++){
                   e.onNext(new Integer(i));
               }
               e.onError(new Throwable("testttt"));
               //e.onComplete();
           }
       }, BackpressureStrategy.ERROR)
               .subscribeOn(Schedulers.io())
               .observeOn(Schedulers.newThread())
               .subscribe(new Consumer<Integer>() {
                   @Override
                   public void accept(Integer integer) throws Exception {
                       Log.v(TAG,"kevin integer=="+integer.intValue());
                   }
               }, new Consumer<Throwable>() {
                   @Override
                   public void accept(Throwable throwable) throws Exception {
                       Log.v(TAG,"kevin throwable=="+throwable);
                   }
               }, new Action() {
                   @Override
                   public void run() throws Exception {
                       Log.v(TAG,"kevin onComplete==");
                   }
               }, new Consumer<Subscription>() {
                   @Override
                   public void accept(Subscription subscription) throws Exception {
                       subscription.request(50);
                   }
               });
    }



    private void mergeDelayError() {
        Observable.mergeArrayDelayError(
                Observable.create(new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                        emitter.onNext(1);
                        emitter.onNext(2);
                        emitter.onNext(3);
                        // 发送Error事件，因为使用了concatDelayError，所以第2个Observable将会发送事件，等发送完毕后，再发送错误事件
                        emitter.onError(new NullPointerException("这里发送了一个onError()"));
                        emitter.onComplete();
                    }
                }),Observable.just(4, 5, 6))
                .subscribe(intObserver);
    }

    private Observer<Integer> intObserver = new Observer<Integer>() {
        @Override
        public void onSubscribe(Disposable d) {
            Log.e(TAG,"开始采用subscribe连接");
        }

        @Override
        public void onNext(Integer integer) {
            Log.e(TAG,"事件 = " + integer);
        }

        @Override
        public void onError(Throwable e) {
            Log.e(TAG,e.getMessage());
        }

        @Override
        public void onComplete() {
            Log.e(TAG,"对Complete事件作出响应");
        }
    } ;


    ArrayMap<String,String> maps = new ArrayMap();
    /**
     * 取消所有的订阅
     */

    public void cancelAll() {
        if (maps.isEmpty()) {
            return;
        }
        Set<String> keys = maps.keySet();
        ArrayList<String> lists = new ArrayList<>(keys);
        for (String key :lists){
            cancel(key);
        }


    }

    /**
     * 取消tag对应的订阅
     */
    public void cancel(String tag) {
        if (maps.isEmpty()) {
            return;
        }
        if (maps.get(tag) == null) {
            return;
        }
        //maps.get(tag).cancel();
        Log.v("TAG","kevin tag==="+tag);
        maps.remove(tag);
    }

    private void initView(){
        recyclerView =  (RecyclerView)findViewById(R.id.recycle_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        mNewsAdapter = new NewsAdapter(recyclerView);
        recyclerView.setAdapter(mNewsAdapter);
        RetrofitClient.initClient_BaseUrl(new OkHttpClient(),"http://gank.io");
        presenter.getDetailInfo();
    }

    @Override
    protected MvpTestPresenter getPresenter() {
        return  new MvpTestPresenter(MvpTestActivity.this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_mvptest;
    }

    @Override
    public void refreshDetailInfo(NewsEntity entity) {
        mNewsAdapter.setData(entity.getResults());
        mNewsAdapter.notifyDataSetChanged();
    }

}
