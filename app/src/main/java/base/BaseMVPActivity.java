package base;

/**
 * Created by kai on 2018/4/19.
 */

public abstract class BaseMVPActivity<T extends IPresenter> extends IActivity<T>{

    @Override
    protected int getLayoutId() {
        return getLayout();
    }

    protected abstract int getLayout();

}
