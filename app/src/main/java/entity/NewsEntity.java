package entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kai on 2018/4/16.
 */

public class NewsEntity {

    private boolean error;
    private List<NewsResultEntity> results = new ArrayList<>();

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<NewsResultEntity> getResults() {
        return results;
    }

    public void setResults(List<NewsResultEntity> results) {
        this.results = results;
    }

}
