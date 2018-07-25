package base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import me.yokeyword.fragmentation.SupportActivity;

/**
 * Created by kai on 2018/4/19.
 */

public abstract class IActivity<T extends IPresenter> extends SupportActivity {

    protected T presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        presenter = getPresenter();
    }


    protected abstract int getLayoutId();

    protected abstract T getPresenter();

}
