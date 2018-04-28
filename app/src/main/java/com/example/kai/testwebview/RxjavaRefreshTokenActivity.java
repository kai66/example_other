package com.example.kai.testwebview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.concurrent.Callable;

import Util.TokenLoader;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by kai on 2018/4/18.
 */

public class RxjavaRefreshTokenActivity extends AppCompatActivity {


    private Button bt_submit;
    private String TAG =  "RxjavaRefreshTokenActivity";
    private String ERROR_RETRY = "error_retry";
    private String ERROR_TOKEN ="error_token";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava_refreshtoken);
        initView();
    }

    private void initView(){
        bt_submit = (Button)findViewById(R.id.bt_submit);
        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startRequest(0);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                startRequest(1);
            }
        });
    }

    private void startRequest(final int index) {
        Observable<String> observable = Observable.defer(new Callable<ObservableSource<String>>() {
            @Override
            public ObservableSource<String> call() throws Exception {
                String cacheToken = TokenLoader.getInstance().getCacheToken();
                Log.d(TAG, index + "获取到缓存Token=" + cacheToken);
                return Observable.just(cacheToken);
            }
        }).flatMap(new Function<String, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(String token) throws Exception {
                return getUserObservable(index, token);
            }
        }).retryWhen(new Function<Observable<Throwable>, ObservableSource<?>>() {
            private int mRetryCount = 0;
            @Override
            public ObservableSource<?> apply(Observable<Throwable> throwableObservable) throws Exception {
                return throwableObservable.flatMap(new Function<Throwable, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(Throwable throwable) throws Exception {
                        Log.d(TAG, index + ":" + "发生错误=" + throwable + ",重试次数=" + mRetryCount);
                        if (mRetryCount > 0) {
                            return Observable.error(new Throwable(ERROR_RETRY));
                        } else if (ERROR_TOKEN.equals(throwable.getMessage())) {
                            mRetryCount++;
                            return TokenLoader.getInstance().getNetTokenLocked();
                        } else {
                            return Observable.error(throwable);
                        }
                    }
                });
            }
        });
        DisposableObserver<String> observer = new DisposableObserver<String>() {
            @Override
            public void onNext(String value) {
                Log.d(TAG, index + ":" + "收到信息=" + value);
            }
            @Override
            public void onError(Throwable e) {
                Log.d(TAG, index + ":" + "onError=" + e);
            }
            @Override
            public void onComplete() {
                Log.d(TAG, index + ":" + "onComplete");
            }
        };
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(observer);
    }

    private Observable<String> getUserObservable (final int index, final String token) {
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                Log.d(TAG, index + "使用token=" + token + "发起请求");
                //模拟根据Token去请求信息的过程。
                if (!TextUtils.isEmpty(token) && System.currentTimeMillis() - Long.valueOf(token) < 2000) {
                    e.onNext(index + ":" + token + "的用户信息");
                } else {
                    e.onError(new Throwable(ERROR_TOKEN));
                }
            }
        });
    }

}
