package com.example.kai.testwebview.fragment;

import com.example.kai.testwebview.R;

import me.yokeyword.fragmentation.SupportFragment;
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * Created by kai on 2018/5/12.
 */

public class BaseFragment extends SupportFragment{

    @Override
    protected FragmentAnimator onCreateFragmentAnimator() {
       // return new FragmentAnimator(R.anim.slide_in_left,R.anim.slide_out_right);
        return new DefaultHorizontalAnimator();
    }

}
