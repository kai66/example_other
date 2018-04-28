package com.example.kai.testwebview;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by kai on 2018/3/1.
 */

public class LinkPayTestActivity extends AppCompatActivity {

    Button button ;

    //YouLanHomeActivity.jump2YouLanHomeActivity(Context context, String phoneNumber, String userId, String token, String appid)
    /*
    参数名	类型	说明
    context	Context	上下文对象
    phoneNumber	String	手机号码
    userId	String	用户身份证号(选填)
    token	String	商户授权令牌
    appid	String	商户会员id
    realName String	真实姓名（选填）
    */

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linkpay_test);
        button = (Button)findViewById(R.id.btn_linkpay);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //YouLanHomeActivity.jump2YouLanHomeActivity(LinkPayTestActivity.this,"15800605400","",
               //        "token" ,"appid","");
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
