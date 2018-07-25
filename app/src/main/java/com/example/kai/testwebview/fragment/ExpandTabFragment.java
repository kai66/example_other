package com.example.kai.testwebview.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.kai.testwebview.R;

import java.util.ArrayList;

import me.yokeyword.fragmentation.SupportFragment;
import widget.ExpandTabView;
import widget.ViewLeft;
import widget.ViewMiddle;
import widget.ViewRight;

/**
 * Created by kai on 2018/5/12.
 */

public class ExpandTabFragment extends BaseFragment{

    private ExpandTabView expandTabView;
    private ArrayList<View> mViewArray = new ArrayList<View>();
    private ViewLeft viewLeft;
    private ViewMiddle viewMiddle;
    private ViewRight viewRight;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_expandtab,container,false);
        initView(view);
        initVaule();
        initListener();
        return view;
    }

    private void initView(View view) {
        expandTabView = (ExpandTabView)view.findViewById(R.id.expandtab_view);
        viewLeft = new ViewLeft(_mActivity);
        viewMiddle = new ViewMiddle(_mActivity);
        viewRight = new ViewRight(_mActivity);
    }

    private void initVaule() {

        mViewArray.add(viewLeft);
        mViewArray.add(viewMiddle);
        mViewArray.add(viewRight);
        ArrayList<String> mTextArray = new ArrayList<String>();
        mTextArray.add("距离");
        mTextArray.add("区域");
        mTextArray.add("距离");
        expandTabView.setValue(mTextArray, mViewArray);
        expandTabView.setTitle(viewLeft.getShowText(), 0);
        expandTabView.setTitle(viewMiddle.getShowText(), 1);
        expandTabView.setTitle(viewRight.getShowText(), 2);

    }

    private void initListener() {

        viewLeft.setOnSelectListener(new ViewLeft.OnSelectListener() {

            @Override
            public void getValue(String distance, String showText) {
                onRefresh(viewLeft, showText);
            }
        });

        viewMiddle.setOnSelectListener(new ViewMiddle.OnSelectListener() {

            @Override
            public void getValue(String showText) {

                onRefresh(viewMiddle,showText);

            }
        });

        viewRight.setOnSelectListener(new ViewRight.OnSelectListener() {

            @Override
            public void getValue(String distance, String showText) {
                onRefresh(viewRight, showText);
            }
        });

    }

    private void onRefresh(View view, String showText) {

        expandTabView.onPressBack();
        int position = getPositon(view);
        if (position >= 0 && !expandTabView.getTitle(position).equals(showText)) {
            expandTabView.setTitle(showText, position);
        }
        Toast.makeText(_mActivity, showText, Toast.LENGTH_SHORT).show();

    }

    private int getPositon(View tView) {
        for (int i = 0; i < mViewArray.size(); i++) {
            if (mViewArray.get(i) == tView) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean onBackPressedSupport() {
        if (!expandTabView.onPressBack()) {
           _mActivity.onBackPressed();
        }else{
            _mActivity.onBackPressed();
        }
        return true;
    }

}
