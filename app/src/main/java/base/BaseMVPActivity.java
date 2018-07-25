package base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.kai.testwebview.MainActivity;
import com.example.kai.testwebview.PlatformActivity;
import com.example.kai.testwebview.R;

/**
 * Created by kai on 2018/4/19.
 */

public abstract class BaseMVPActivity<T extends IPresenter> extends IActivity<T>{

    @Override
    protected int getLayoutId() {
        return getLayout();
    }

    protected abstract int getLayout();

    public void startNewActivity(Context context,Class<?> cls,Bundle bundle){
        Intent intent = new Intent();
        intent.setClass(context,cls);
        if(bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        overridePendingTransition(R.anim.h_fragment_enter,R.anim.h_fragment_exit);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }

    /*
    @Override
    public void overridePendingTransition(int enterAnim, int exitAnim) {
        overridePendingTransition(R.anim.v_fragment_enter,R.anim.v_fragment_exit);
    }
    */

}
