package com.example.kai.testwebview;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.kai.testwebview.alarm.AlarmActivity;
import com.example.kai.testwebview.mvptest.MvpTestActivity;

import Util.Utils;
import base.BaseMVPActivity;
import service.LocalService;
import service.RemoteService;
import widget.AnimType;
import widget.NoticeCenterDialog;
import widget.OnDialogClickListener;
import widget.OnKeyType;
import widget.SimpleOnKeyListener;

public class MainActivity extends AppCompatActivity {

    private EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Utils.setApplication(MainActivity.this.getApplication());

        View view = (View)findViewById(R.id.content_main);
        Button Button_WebView = (Button)findViewById(R.id.button_webview);
        Button_WebView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                final NoticeCenterDialog noticeCenterDialog = new NoticeCenterDialog(MainActivity.this);
                noticeCenterDialog.setOnDialogClickListener(new OnDialogClickListener() {
                    @Override
                    public void onDialogClick(Dialog dialog, int id) {
                        if(R.id.ok == id){
                            Toast.makeText(MainActivity.this,"ok",Toast.LENGTH_SHORT).show();
                            noticeCenterDialog.dismiss();
                            Intent intent = new Intent();
                            intent.setClass(MainActivity.this,AlarmActivity.class);
                            startActivity(intent);
                        }else if(R.id.cancle == id){
                            Toast.makeText(MainActivity.this,"cancle",Toast.LENGTH_SHORT).show();
                            noticeCenterDialog.dismiss();
                            Intent intent = new Intent();
                            intent.setClass(MainActivity.this,SwipCardActivity.class);
                            startActivity(intent);
                        }
                    }
                });
                noticeCenterDialog.show(AnimType.CENTER_NORMAL);
                  */
                /*
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,StepViewActivity.class);
                startActivity(intent);

                Intent intent = new Intent();
                intent.setClass(MainActivity.this,RoundBackActivity.class);
                startActivity(intent);
                  */
               /*
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,WebViewActivity.class);
                startActivity(intent);
                  */

               /*
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,RecyclerViewActivity.class);
                startActivity(intent);
                */
               /*
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,FlexboxLayoutActivity.class);
                startActivity(intent);
                */


               /*
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,PieChartActivity.class);
                startActivity(intent);
                */

                Bundle bundle = new Bundle();
                bundle.putInt("type",1);
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,PlatformActivity.class);
                if(bundle != null) {
                    intent.putExtras(bundle);
                }
                startActivity(intent);
               /*
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,ExpandActivity.class);
                startActivity(intent);
                */

                /*
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,RlTextViewActivity.class);
                startActivity(intent);
                */
               /*
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,RxjavaActivity.class);
                startActivity(intent);
                */
               /*
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,RetrofitActivity.class);
                startActivity(intent);
                */

/*
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,RxjavaRefreshTokenActivity.class);
                startActivity(intent);
*/

                /*
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,MvpTestActivity.class);
                startActivity(intent);
                */

                /*
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,LineViewActivity.class);
                startActivity(intent);
                */
            }
        });
        ImageView imageview = (ImageView)view.findViewById(R.id.imageview);
        imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast =  Toast.makeText(MainActivity.this,"ImageView Click",Toast.LENGTH_LONG);
                //toast.setText("ImageView Click");
                toast.show();
                startService(new Intent(MainActivity.this, LocalService.class));
                startService(new Intent(MainActivity.this, RemoteService.class));
               // sendNotification();
            }
        });

        Button mInfiniteCycleButton =  (Button)findViewById(R.id.infiniteCycleViewPager);
        mInfiniteCycleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new  Intent("com.youlan.alarmset.service.restart");
                //intent.setAction("com.alarm.service.restart");
                intent.putExtra("msg","restart");
                Log.v("kevin","sendBroadcast");
                sendBroadcast(intent);
            }
        });

        mEditText = (EditText)findViewById(R.id.edittext);
        mEditText.addTextChangedListener(mTextWatcher);

       // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }


    private void sendNotification() {
        Log.v("kevin","sendNotification");
        //获取NotificationManager实例
        NotificationManager notifyManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        //实例化NotificationCompat.Builde并设置相关属性
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(
                        BitmapFactory.decodeResource(getResources(),
                                R.mipmap.ic_launcher))
                //点击通知后自动清除
                .setAutoCancel(true)
                .setContentTitle("我是带Action的Notification")
                .setContentText("点我会打开MainActivity")
                .setContentInfo("test")
                .setTicker("ticker")
                .setWhen(System.currentTimeMillis())
                .setShowWhen(true)
                .setVisibility(View.VISIBLE);

        // .setContentIntent(mainPendingIntent);
        //设置通知时间，默认为系统发出通知的时间，通常不用设置
        //.setWhen(System.currentTimeMillis());
        //通过builder.build()方法生成Notification对象,并发送通知,id=1
        // 3.发出通知
        Notification notification = null;
        if(Build.VERSION.SDK_INT>=16){
            notification=builder.build();
        }else{
            notification=builder.getNotification();
        }
       // notification.flags |= Notification.FLAG_AUTO_CANCEL;// 
        notifyManager.notify(1000,notification);
    }

    private TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {
            Log.v("TAG","kevin beforeTextChanged charSequence="+charSequence.toString()+" start="+start+" before="+before+" count="+count);
            if(charSequence.toString().contains(".")){

            }
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
            Log.v("TAG","kevin onTextChanged charSequence="+charSequence.toString()+" start="+start+" before="+before+" count="+count);
            String string = charSequence.toString();
            if(string.indexOf(".") != string.lastIndexOf(".")){
               String beforestring = string.substring(0,string.indexOf("."));
               Log.v("TAG","kevin string="+string);
               String afterstring = string.substring(string.indexOf("."),string.lastIndexOf("."));
               Log.v("TAG","kevin beforestring="+beforestring+" afterstring="+afterstring);
            }
            //mEditText.setText(string);
            //mEditText.setSelection(string.length());
        }

        @Override
        public void afterTextChanged(Editable editable) {
            Log.v("TAG","kevin afterTextChanged editable="+editable.toString());
            String string = editable.toString().trim();
            String finalString ="";
            if (string.length() - 1 - string.indexOf(".") > 2 ) {
                finalString = string.substring(0, string.indexOf(".") + 3);
                mEditText.setText(finalString);
                mEditText.setSelection(finalString.length());
            }

            /*
            if(string.indexOf(".") != string.lastIndexOf(".")){
                String beforestring = string.substring(0,string.indexOf("."));
                Log.v("TAG","kevin string="+string);
                String afterstring = string.substring(string.indexOf("."),string.lastIndexOf("."));
                Log.v("TAG","kevin beforestring="+beforestring+" afterstring="+afterstring);
                String finalString = beforestring+afterstring;
                mEditText.setText(finalString);
                mEditText.setSelection(finalString.length());
            }
            */
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.v("kevin","onBackPressed");
        MainActivity.this.finish();
        //System.exit(0);
    }
}
