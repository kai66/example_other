package com.example.kai.testwebview.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kai.testwebview.MainActivity;
import com.example.kai.testwebview.PlatformActivity;
import com.example.kai.testwebview.R;
import com.example.kai.testwebview.RecyclerViewActivity;

import org.w3c.dom.Text;

import base.BaseMVPActivity;
import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation.SupportFragment;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * Created by kai on 2018/5/12.
 */

public class FragmentOne extends BaseFragment{

     NestedScrollView nested_scrollview;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_one,container,false);
        initView(view);
        return view;
    }

    private void initView(View view){

        nested_scrollview = view.findViewById(R.id.nested_scrollview);

        nested_scrollview.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

            }
        });

        nested_scrollview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });


        view.findViewById(R.id.img_01).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(_mActivity,"click img_01",Toast.LENGTH_LONG).show();
            }
        });

        view.findViewById(R.id.img_02).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(_mActivity,"click img_02",Toast.LENGTH_LONG).show();
            }
        });

        view.findViewById(R.id.img_03).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(_mActivity,"click img_03",Toast.LENGTH_LONG).show();
            }
        });


        view.findViewById(R.id.bt_one).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //start(new FragmentTwo());
                Bundle bundle = new Bundle();
                bundle.putInt("type",2);
                ((BaseMVPActivity)_mActivity).startNewActivity(getContext(),PlatformActivity.class,bundle);
            }
        });

        view.findViewById(R.id.bt_two).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start(new NestedScrollViewFragment());
            }
        });

        view.findViewById(R.id.bt_three).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start(new AlgorithmFragment());
            }
        });

        view.findViewById(R.id.bt_four).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(new GalleryFinalFragment());
            }
        });

        view.findViewById(R.id.bt_five).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(new NewFileFragment());
            }
        });

        view.findViewById(R.id.bt_six).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(_mActivity,RecyclerViewActivity.class);
                startActivity(intent);
            }
        });

        view.findViewById(R.id.bt_sevent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               start(new ScrollViewFragment());
            }
        });

        view.findViewById(R.id.bt_eight).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(new HtmlFragment());
            }
        });

        view.findViewById(R.id.bt_nine).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(new MutilLayoutFragment());
            }
        });

        view.findViewById(R.id.bt_ten).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(new DetailInfoFragment());
            }
        });

        view.findViewById(R.id.bt_eleven).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(new SmartBarFragment());
            }
        });

        view.findViewById(R.id.bt_textdrawable).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(new TextDrawableFragment());
            }
        });

        view.findViewById(R.id.bt_gallery).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(new GalleryEffectFragment());
            }
        });

        view.findViewById(R.id.bt_contract).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(new ContractFragment());
            }
        });



    }


}
