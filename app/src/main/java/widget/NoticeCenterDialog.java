package widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;

import com.example.kai.testwebview.MainActivity;
import com.example.kai.testwebview.R;

/**
 * Created by kai on 2018/1/13.
 */

public class NoticeCenterDialog<D extends NoticeCenterDialog> extends BaseDialog<D>{

    public NoticeCenterDialog(@NonNull Context context) {
        super(context, R.style.dialog_notice_center);
        this.setScaleWidth(0.8f);
        this.setGravity(Gravity.CENTER);
    }

    @Override
    public void onClick(View v, int id) {
        if(onDialogClickListener != null) {
            onDialogClickListener.onDialogClick(this, id);
        }
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void show(int animType) {
        super.show(animType);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.dialog_center_notice;
    }

    @Override
    public void onCreateData() {
        setOnCilckListener(R.id.ok,R.id.cancle);
    }



}
