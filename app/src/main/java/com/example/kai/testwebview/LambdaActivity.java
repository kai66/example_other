package com.example.kai.testwebview;

import android.annotation.TargetApi;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * Created by kai on 2018/3/29.
 */

public class LambdaActivity extends AppCompatActivity {

    Button btnLambda;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lambda);
        initView();
        initLambda();
    }


    private void initView(){
        btnLambda = (Button)findViewById(R.id.btn_lambda);
    }

    private void initLambda(){
        List languages = Arrays.asList("Java", "Scala", "C++", "Haskell", "Lisp");

        btnLambda.setOnClickListener(v -> new Thread(() -> Log.d("lambda", "onclick")).start());

        filter(languages, (str)->str.toString().startsWith("J"));

    }

    @TargetApi(24)
    public static void filter2(List names, Predicate condition) {
       names.stream().filter((name) ->(condition.test(name))).forEach((name) -> {System.out.print(name + "");
       });
    }

    @TargetApi(24)
    public void filter(List names, Predicate condition) {
        names.stream().filter((name) -> (condition.test(name)))
                .forEach((name) -> {System.out.println(name + " ");
                });
    }


}
