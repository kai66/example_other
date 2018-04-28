package com.example.kai.testwebview.mvptest;

import android.support.annotation.Nullable;

import entity.NewsEntity;
import io.reactivex.Observable;

/**
 * Created by kai on 2018/4/19.
 */

public class MvpTestRequestManager {

    static Observable<NewsEntity> getDetail(@Nullable String category, @Nullable int count, @Nullable int page ){
       return RetrofitClient.getInstance().api().getNews(category,count,page);
   }

}
