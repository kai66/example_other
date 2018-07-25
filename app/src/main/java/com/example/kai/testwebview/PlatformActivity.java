package com.example.kai.testwebview;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.kai.testwebview.fragment.ExpandTabFragment;
import com.example.kai.testwebview.fragment.FragmentOne;
import com.example.kai.testwebview.fragment.FragmentTwo;

import base.BaseMVPActivity;
import base.IPresenter;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * Created by kai on 2018/5/12.
 */

public class PlatformActivity extends BaseMVPActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        int indexType = extras.getInt("type", -1);
        switch (indexType) {
            case 1:
                loadRootFragment(R.id.content, new FragmentOne());
            break;
            case 2:
                loadRootFragment(R.id.content, new FragmentTwo());
                break;
            case 3:
                loadRootFragment(R.id.content, new ExpandTabFragment());
                break;
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_platform;
    }

    @Override
    protected IPresenter getPresenter() {
        return null;
    }
}
