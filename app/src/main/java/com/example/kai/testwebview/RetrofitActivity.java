package com.example.kai.testwebview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import Adapter.NewsAdapter;
import entity.NewsEntity;
import entity.NewsResultEntity;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import service.NewsApi;

/**
 * Created by kai on 2018/4/16.
 */

public class RetrofitActivity extends AppCompatActivity {

    EditText et_name;
    EditText et_password;
    Button bt_request;
    RecyclerView recyclerView;
    private int mCurrentPage = 1;
    private NewsAdapter mNewsAdapter;
    private List<NewsResultEntity> mNewsResultEntities = new ArrayList<>();
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    private PublishSubject<String> mNamePublishSubject =  PublishSubject.create();
    private PublishSubject<String> mPasswordPublishSubject =  PublishSubject.create();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);
        initView();
       // initEditObserver();
    }

    private void initEditObserver(){
        bt_request.setEnabled(false);
        Observable<Boolean> observable = Observable.combineLatest(mNamePublishSubject, mPasswordPublishSubject, new BiFunction<String, String, Boolean>() {
            @Override
            public Boolean apply(String s, String s2) throws Exception {
                return s.length()>2 && s.length()<8 && s2.length()>5 && s2.length()<10;
            }
        });

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }
                    @Override
                    public void onNext(Boolean value) {
                        bt_request.setEnabled(value);
                    }
                    @Override
                    public void onError(Throwable e) {

                    }
                    @Override
                    public void onComplete() {

                    }
                });

    }

    private void initView(){
        et_name = (EditText)findViewById(R.id.et_name);
        et_password = (EditText)findViewById(R.id.et_password);
        et_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                mNamePublishSubject.onNext(editable.toString().trim());
            }
        });
        et_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                mPasswordPublishSubject.onNext(editable.toString().trim());
            }
        });
        bt_request = (Button)findViewById(R.id.bt_request);
        bt_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // refreshArticle(++mCurrentPage);
              //  startFixedDelayTime();
              //  startUnfixedDelayTime();
                startRetryRequest();
            }
        });
        recyclerView =  (RecyclerView)findViewById(R.id.recycle_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        mNewsAdapter = new NewsAdapter(recyclerView);
        recyclerView.setAdapter(mNewsAdapter);
        refreshArticle(++mCurrentPage);
    }

    private static final String TAG = RetrofitActivity.class.getSimpleName();
    private static final String MSG_WAIT_SHORT = "wait_short";
    private static final String MSG_WAIT_LONG = "wait_long";
    private int mMsgIndex;
    private static final String[] MSG_ARRAY = new String[] {
            MSG_WAIT_SHORT,
            MSG_WAIT_SHORT,
            MSG_WAIT_LONG,

            MSG_WAIT_LONG
    };


    private void startRetryRequest() {
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                int msgLen = MSG_ARRAY.length ;
                doWork();
                //模拟请求的结果，前四次都返回失败，并将失败信息递交给retryWhen。
                if (mMsgIndex < msgLen) { //模拟请求失败的情况。
                    e.onError(new Throwable(MSG_ARRAY[mMsgIndex]));
                    mMsgIndex++;
                } else { //模拟请求成功的情况。
                    e.onNext("Work Success");
                    e.onComplete();
                }
            }

        }).retryWhen(new Function<Observable<Throwable>, ObservableSource<?>>() {
            private int mRetryCount;
            @Override
            public ObservableSource<?> apply(Observable<Throwable> throwableObservable) throws Exception {
                return throwableObservable.flatMap(new Function<Throwable, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(Throwable throwable) throws Exception {
                        String errorMsg = throwable.getMessage();
                        long waitTime = 0;
                        switch (errorMsg) {
                            case MSG_WAIT_SHORT:
                                waitTime = 2000;
                                break;
                            case MSG_WAIT_LONG:
                                waitTime = 4000;
                                break;
                            default:
                                break;
                        }
                        Log.d(TAG, "发生错误，尝试等待时间=" + waitTime + ",当前重试次数=" + mRetryCount);
                        mRetryCount++;
                        return waitTime > 0 && mRetryCount <= 4 ? Observable.timer(waitTime, TimeUnit.MILLISECONDS) : Observable.error(throwable);
                    }
                });
            }
        });
        DisposableObserver<String> disposableObserver = new DisposableObserver<String>() {
            @Override
            public void onNext(String value) {
                Log.d(TAG, "DisposableObserver onNext=" + value);
            }
            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "DisposableObserver onError=" + e);
            }
            @Override
            public void onComplete() {
                Log.d(TAG, "DisposableObserver onComplete");
            }
        };
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(disposableObserver);
        mCompositeDisposable.add(disposableObserver);
    }

    private void doWork() {
        long workTime = (long) (Math.random() * 500) + 500;
        try {
            Log.d(TAG, "doWork start,  threadId=" + Thread.currentThread().getId());
            Thread.sleep(workTime);
            Log.d(TAG, "doWork finished");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private int mRepeatCount = 0;
    private void startUnfixedDelayTime(){
        Observable<Long> observable =Observable.just(0L)
                .doOnNext(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Log.v("kevin","kevin aLong:"+aLong);
                    }
                })
                .doOnComplete(new Action() {
            @Override
            public void run() throws Exception {
               Log.v("kevin","kevin run");
            }
        })
                .repeatWhen(new Function<Observable<Object>, ObservableSource<Long>>() {
                    @Override
                    public ObservableSource<Long> apply(Observable<Object> objectObservable) throws Exception {
                        Log.v("kevin","kevin apply");
                        return objectObservable.flatMap(new Function<Object, ObservableSource<Long>>() {
                            @Override
                            public ObservableSource<Long> apply(Object o) throws Exception {
                                if (++mRepeatCount > 4) {
                                    //return Observable.empty(); //发送onComplete消息，无法触发下游的onComplete回调。
                                    return Observable.error(new Throwable("Polling work finished")); //发送onError消息，可以触发下游的onError回调。
                                }
                                return Observable.timer(3000 + mRepeatCount * 1000, TimeUnit.MILLISECONDS);
                            }

                        });
                    }
                });

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(Long value) {
                        Log.v("kevin","kevin value="+value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.v("kevin","kevin onError=");
                    }

                    @Override
                    public void onComplete() {
                        Log.v("kevin","kevin onComplete=");
                    }
                });

    }

    private void startFixedDelayTime() {
        Observable<Long> observable = Observable.intervalRange(0, 5, 0, 2000, TimeUnit.MILLISECONDS)
                .take(5).doOnNext(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Log.v("kevin", "kevin aLong=" + aLong);
                    }
                });
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(Long value) {
                        Log.v("kevin", "kevin value=" + value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.v("kevin", "kevin onError");
                    }

                    @Override
                    public void onComplete() {
                        Log.v("kevin", "kevin onComplete");
                    }
                });

    }

    private void refreshArticle(int page) {
        Observable<List<NewsResultEntity>> observable = Observable.just(page)
                .subscribeOn(Schedulers.io())
                .flatMap(new Function<Integer, ObservableSource<List<NewsResultEntity>>>() {
                    @Override
                    public ObservableSource<List<NewsResultEntity>> apply(Integer page) throws Exception {
                        Observable<NewsEntity> androidNews = getObservable("Android", page);
                        Observable<NewsEntity> iosNews = getObservable("iOS", page);
                        return Observable.zip(androidNews, iosNews, new BiFunction<NewsEntity, NewsEntity, List<NewsResultEntity>>() {
                            @Override
                            public List<NewsResultEntity> apply(NewsEntity androidEntity, NewsEntity iosEntity) throws Exception {
                                Log.v("TAG","kevin  androidNews="+androidEntity);
                                List<NewsResultEntity> result = new ArrayList<>();
                                result.addAll(androidEntity.getResults());
                                result.addAll(iosEntity.getResults());
                                return result;
                            }
                        });
                    }
                });
        DisposableObserver<List<NewsResultEntity>> disposable = new DisposableObserver<List<NewsResultEntity>>() {
            @Override
            public void onNext(List<NewsResultEntity> value) {
                Log.v("TAG","kevin  onNext="+value.size());
                mNewsResultEntities.clear();
                mNewsResultEntities.addAll(value);
                mNewsAdapter.setData(mNewsResultEntities);
                mNewsAdapter.notifyDataSetChanged();
            }
            @Override
            public void onError(Throwable e) {

            }
            @Override
            public void onComplete() {

            }
        };
        observable.observeOn(AndroidSchedulers.mainThread()).subscribe(disposable);
        mCompositeDisposable.add(disposable);
    }

    private Observable<NewsEntity> getObservable(String category, int page) {
        NewsApi api = new Retrofit.Builder()
                .baseUrl("http://gank.io")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build().create(NewsApi.class);
        return api.getNews(category, 10, page);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCompositeDisposable.clear();
    }

}
