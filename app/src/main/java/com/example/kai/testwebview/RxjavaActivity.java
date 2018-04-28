package com.example.kai.testwebview;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by kai on 2018/4/16.
 */

public class RxjavaActivity extends AppCompatActivity {

    TextView tv_tip;
    Button bt_start;
    EditText ed_search;

    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private PublishSubject mPublishSubject;
    private SourceHandler mSourceHandler;

    private PublishSubject<String> mStringPublishSubject;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava);
        initView();
    }

    private void initView(){
        tv_tip = (TextView)findViewById(R.id.tv_tip);
        ed_search = (EditText)findViewById(R.id.ed_search);
        bt_start = (Button)findViewById(R.id.bt_start);
        bt_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // startDowload();
                startCalTemperture();
            }
        });
        initSearch();
    }

    private void initSearch() {
        ed_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                mStringPublishSubject.onNext(editable.toString().trim());
            }
        });
        mStringPublishSubject = PublishSubject.create();
        mStringPublishSubject.debounce(300, TimeUnit.MILLISECONDS).filter(new Predicate<String>() {
            @Override
            public boolean test(String s) throws Exception {
                return s.length() > 0;
            }
        }).switchMap(new Function<String, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(String query) throws Exception {
                return Observable.create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(ObservableEmitter<String> observableEmitter) throws Exception {
                        try {
                            Thread.sleep(100 + (long) (Math.random() * 500));
                        } catch (InterruptedException e) {
                            if (!observableEmitter.isDisposed()) {
                                observableEmitter.onError(e);
                            }
                        }
                        observableEmitter.onNext("完成搜索，关键词为：" + query);
                        observableEmitter.onComplete();
                    }
                }).subscribeOn(Schedulers.io());
            }
        })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(String value) {
                        tv_tip.setText(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    public void updateTemperature(int temperature) {
        Log.d("updateTemperature", "updateTemperature：" + temperature);
        mPublishSubject.onNext(temperature);
    }

    private class SourceHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int temperature = (int)(Math.random() * 10)+ 5;
            updateTemperature(temperature);
            //循环地发送。
            sendEmptyMessageDelayed(0, 250 + (int) (250 * Math.random()));
        }
    }

    private void startCalTemperture(){
        mPublishSubject = PublishSubject.create();
        mPublishSubject.buffer(3000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Integer>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }
                    @Override
                    public void onNext(List<Integer> value) {
                        if (value != null && value.size() > 0) {
                            int sum = 0;
                            for (int i : value) {
                                sum += i;
                            }
                            int result = sum / value.size();
                            tv_tip.setText(value.size() + "个数据，平均值为：" + result);
                        }
                    }
                    @Override
                    public void onError(Throwable e) {
                        tv_tip.setText("Error");
                    }
                    @Override
                    public void onComplete() {
                        tv_tip.setText("Complete");
                    }
                });
        mSourceHandler = new SourceHandler();
        mSourceHandler.sendEmptyMessage(0);
    }

    private void startDowload(){
         Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                for (int i = 0; i < 100; i++) {
                    if (i % 20 == 0) {
                        try {
                            Thread.sleep(500); //模拟下载的操作。
                        } catch (InterruptedException exception) {
                            if (!e.isDisposed()) {
                                e.onError(exception);
                            }
                        }
                        e.onNext(i);
                    }
                }
                e.onComplete();
            }
        });
        DisposableObserver<Integer> disposableObserver = new DisposableObserver<Integer>() {
            @Override
            public void onNext(Integer value) {
                Log.d("BackgroundActivity", "onNext=" + value);
                tv_tip.setText("Current Progress=" + value);
            }
            @Override
            public void onError(Throwable e) {
                Log.d("BackgroundActivity", "onError=" + e);
                tv_tip.setText("Download Error");
            }
            @Override
            public void onComplete() {
                Log.d("BackgroundActivity", "onComplete");
                tv_tip.setText("Download onComplete");
            }

        };
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                            mCompositeDisposable.add(d);
                    }
                    @Override
                    public void onNext(Integer value) {
                        tv_tip.setText("Current Progress=" + value);
                    }
                    @Override
                    public void onError(Throwable e) {
                        tv_tip.setText("Download Error");
                    }
                    @Override
                    public void onComplete() {
                        tv_tip.setText("Download onComplete");
                    }
                });
                //.subscribe(disposableObserver);
        //mCompositeDisposable.add(disposableObserver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCompositeDisposable.clear();
        if (mSourceHandler != null) {
            mSourceHandler.removeCallbacksAndMessages(null);
        }
    }

}
