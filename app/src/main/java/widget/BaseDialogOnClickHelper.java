package widget;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by kai on 2018/1/13.
 */

public class BaseDialogOnClickHelper {

    private static BaseDialogOnClickHelper OnClickHelper;

    private BaseDialogOnClickHelper() {
    }

    public static BaseDialogOnClickHelper getInstance() {
        if(OnClickHelper == null) {
            Class var0 = BaseDialogOnClickHelper.class;
            synchronized(BaseDialogOnClickHelper.class) {
                OnClickHelper = OnClickHelper == null?new BaseDialogOnClickHelper():OnClickHelper;
            }
        }

        return OnClickHelper;
    }

    public void onBindClickListener(@NonNull View contentView, @NonNull View.OnClickListener onClickListener) {
        this.setOnClickListener(contentView, onClickListener);
    }

    private void setOnClickListener(@NonNull View view, @NonNull View.OnClickListener onClickListener) {
        if(view == null) {
            //LogUtil.w("控件为空");
        } else {
            view.setOnClickListener(onClickListener);
            if(view instanceof ViewGroup) {
                this.setOnClickListener((ViewGroup)view, onClickListener);
            }

        }
    }

    private void setOnClickListener(@NonNull ViewGroup viewGroup, @NonNull View.OnClickListener onClickListener) {
        try {
            viewGroup.setOnClickListener(onClickListener);

            for(int e = 0; e < viewGroup.getChildCount(); ++e) {
                View v = viewGroup.getChildAt(e);
                if(v instanceof ViewGroup) {
                    this.setOnClickListener((ViewGroup)v, onClickListener);
                } else if(v != null && v instanceof View) {
                    v.setOnClickListener(onClickListener);
                }
            }
        } catch (Exception var5) {
            //LogUtil.e(var5);
        }
    }

}
