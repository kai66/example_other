package com.example.kai.testwebview.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.TimePickerView;
import com.example.kai.testwebview.R;
import com.example.kai.testwebview.adapter.JoneBaseAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import Util.DensityUtil;
import Util.DividerItemDecoration;
import Util.SpaceItemDecoration;
import cn.bingoogolapple.androidcommon.adapter.BGAOnRVItemClickListener;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;
import widget.EditTextWithDelete;
import widget.PayPwdEditText;

/**
 * Created by kai on 2018/6/21.
 */

public class GalleryEffectFragment extends BaseFragment{

    private RecyclerView mRecycleView;
    private JoneBaseAdapter joneBaseAdapter;
    private int mScreenWidth;
    private static final float MIN_SCALE = 0.85f;
    private static final float MAX_SCALE = 1.1f;
    private LinearLayoutManager mLinearLayoutManager;
    private int mMinWidth;
    private int mMaxWidth;

    private ArrayList<String> ListArrays = new ArrayList<>();
    PayPwdEditText pwd_et;

    EditTextWithDelete edt_search_circle;

    TextView tv_cancle;

    private  String pwd_string ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_gallery_effect,container,false);
        Log.v("TAG","brancher00003test");
        mRecycleView = (RecyclerView)view.findViewById(R.id.recycle_view);

        final TextView tv_choose = (TextView)view.findViewById(R.id.tv_choose);
        tv_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerView mTimePickerView = new TimePickerView(_mActivity,TimePickerView.Type.HOURS_MINS);
                mTimePickerView.setCyclic(true);
                Date date = new Date();
                mTimePickerView.setTime(date);
                mTimePickerView.setCancelable(true);
                mTimePickerView.show();
                mTimePickerView.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date) {
                        tv_choose.setText(getHourAndMinute(date));
                    }
                });
            }
        });

        pwd_et =  (PayPwdEditText)view.findViewById(R.id.pwd_et);

        edt_search_circle = (EditTextWithDelete)view.findViewById(R.id.edt_search_circle);
        edt_search_circle.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                   Toast.makeText(_mActivity,edt_search_circle.getText().toString().trim(),Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });
        tv_cancle = (TextView)view.findViewById(R.id.tv_cancle);
        tv_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt_search_circle.setText("");
            }
        });

        pwd_et.initStyle(R.drawable.edit_num_bg, 4, 15f, R.color.transparent, R.color.color_999, 20);
        pwd_et.setShowPwd(true);

        pwd_et.setOnTextFinishListener(new PayPwdEditText.OnTextFinishListener() {
            @Override
            public void onFinish(String str) {
               // pwd_string = str;
                pwd_string = pwd_et.getPwdText();
                Toast.makeText(_mActivity,pwd_string,Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    //获得时分
    private String getHourAndMinute(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        return simpleDateFormat.format(date);
    }


    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mLinearLayoutManager = new LinearLayoutManager(_mActivity);
        mLinearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        mRecycleView.setLayoutManager(mLinearLayoutManager);
        mScreenWidth = getResources().getDisplayMetrics().widthPixels;
        mRecycleView.addItemDecoration(new SpaceItemDecoration(10,0));
        //mMinWidth =  DensityUtil.dip2px(_mActivity,80);
        //mMaxWidth =  DensityUtil.dip2px(_mActivity,100);
        ListArrays = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            ListArrays.add("naem"+i);
        }
        mRecycleView.addOnScrollListener(mOnScrollListener);
        joneBaseAdapter = new JoneBaseAdapter(mRecycleView,R.layout.item_list) {
            @Override
            public void fillItemData(BGAViewHolderHelper helper, int position, Object model) {
                helper.setText(R.id.tv_title,ListArrays.get(position));
                helper.setImageResource(R.id.iv_pic,R.mipmap.ic_launcher_round);
            }
        };
        mRecycleView.setAdapter(joneBaseAdapter);
        joneBaseAdapter.setData(ListArrays);
        joneBaseAdapter.setOnRVItemClickListener(new BGAOnRVItemClickListener() {
            @Override
            public void onRVItemClick(ViewGroup parent, View itemView, int position) {
                Toast.makeText(_mActivity,"click "+position,Toast.LENGTH_LONG).show();
            }
        });
    }


    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            final int childCount = recyclerView.getChildCount();
            for (int i = 0; i < childCount; i++) {
                RelativeLayout child = (RelativeLayout) recyclerView.getChildAt(i);
                RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) child.getLayoutParams();
                //lp.rightMargin = 5;
                lp.height = DensityUtil.dip2px(_mActivity,100);
                int left = child.getLeft();
                int right = child.getRight();
                int halfwidth = mScreenWidth/2;
                if(halfwidth>left && halfwidth<right){
                    float scaleFactor = 80/60;
                    lp.width =   DensityUtil.dip2px(_mActivity,80);;
                    child.setLayoutParams(lp);
                    child.setScaleY(scaleFactor);
                }else{
                    lp.width =   DensityUtil.dip2px(_mActivity,60);;
                    child.setLayoutParams(lp);
                    child.setScaleY(1);
                }
                /*
                final float percent = left < 0 || right < 0 ? 0 : Math.min(left, right) * 1f / Math.max(left, right);
                Log.e("tag", "percent = " + percent);
                float scaleFactor = MIN_SCALE + Math.abs(percent) * (MAX_SCALE - MIN_SCALE);
                int width = (int) (mMinWidth + Math.abs(percent) * (mMaxWidth - mMinWidth));
                */

            }
        }
    };

}
