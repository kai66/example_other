package com.example.kai.testwebview.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.kai.testwebview.R;

/**
 * Created by kai on 2018/5/23.
 */

public class AlgorithmFragment extends BaseFragment{

    EditText editText01;
    EditText editText02;
    Button button;
    TextView textView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_algorithm,container,false);
        initView(view);
       return view ;
    }

    private void initView(View view){
        editText01 = (EditText)view.findViewById(R.id.et_input01);
        editText02 = (EditText)view.findViewById(R.id.et_input02);
        button = (Button)view.findViewById(R.id.bt_start);
        textView = (TextView) view.findViewById(R.id.tv_result);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int ed01 =Integer.valueOf(editText01.getText().toString().trim()).intValue();
                int ed02 =Integer.valueOf(editText02.getText().toString().trim()).intValue();
                int result = gcd(ed01,ed02);
                textView.setText(String.valueOf(result));
            }
        });
    }

    private int gcd(int p,int q){
        if(q==0){
            return p;
        }else{
            int r =  p%q;
            return gcd(q,r);
        }
    }

}
