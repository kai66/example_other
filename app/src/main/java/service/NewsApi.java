package service;

import entity.NewsEntity;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by kai on 2018/4/16.
 */

public interface NewsApi {

    @GET("api/data/{category}/{count}/{page}")
    Observable<NewsEntity> getNews(@Path("category") String category, @Path("count") int count, @Path("page") int page);

}
