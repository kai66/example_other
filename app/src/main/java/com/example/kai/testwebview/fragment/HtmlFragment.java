package com.example.kai.testwebview.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kai.testwebview.R;

import org.sufficientlysecure.htmltextview.HtmlHttpImageGetter;
import org.sufficientlysecure.htmltextview.HtmlTextView;

/**
 * Created by kai on 2018/6/8.
 */

public class HtmlFragment extends BaseFragment{

    HtmlTextView htmlTextView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View view =  inflater.inflate(R.layout.fragment_html,container,false);
        htmlTextView = view.findViewById(R.id.html_text);
        return view;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        initView();
    }

   private void initView(){
       htmlTextView.setHtml("<h2>Hello wold</h2><img src=\"http://www.example.com/cat_pic.png\"/>",
               new HtmlHttpImageGetter(htmlTextView));
    }

}
