package base;

import android.view.View;

/**
 * Created by kai on 2018/4/19.
 */

public class BaseMVPPresenter<T> implements IPresenter {

    protected T mView;//Presenter持有的View

    public BaseMVPPresenter(T view) {
        this.mView = view;
    }

}
