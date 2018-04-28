package widget;

import android.app.Dialog;
import android.support.annotation.IdRes;

/**
 * Created by kai on 2018/1/13.
 */

public interface OnDialogClickListener<D extends Dialog>{

    public  void onDialogClick(D dialog,@IdRes int id);

}
