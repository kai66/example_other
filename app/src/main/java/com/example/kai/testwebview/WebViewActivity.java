package com.example.kai.testwebview;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import java.io.File;
import java.util.HashMap;
import java.util.Set;

public class WebViewActivity extends AppCompatActivity {

    public WebView mWebVie;
    public String fileFullName;//照相后的照片的全整路径
    private boolean fromTakePhoto; //是否是从摄像界面返回的webview
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        View view = (View)findViewById(R.id.content_webview);
        mWebVie = (WebView)findViewById(R.id.webview);
        initWebView();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Button button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WebViewActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.v("TAG","kevin ");
                        //String s = "javascript:checkResumeInfo('android','" +"tokenyyy" + "','" + "client_id0001" + "')";
                        //String s = "javascript:checkResumeInfo('android','" +"tokenyyy" + "','" + "client_id0001" + "')";
                        String s = "javascript:toCheck('android'" +")";
                        mWebVie.loadUrl(s);
                    }
                });
            }
        });

    }

   void initWebView() {
       WebSettings setting = mWebVie.getSettings();
       setting.setJavaScriptEnabled(true);
       setting.setJavaScriptCanOpenWindowsAutomatically(true);

       mWebVie.setWebViewClient(new WebViewClient() {
           @Override
           public void onPageStarted(WebView view, String url, Bitmap favicon) {
               super.onPageStarted(view, url, favicon);
           }

           @Override
           public boolean shouldOverrideUrlLoading(WebView view, String url) {
               return super.shouldOverrideUrlLoading(view, url);
           }

           @Override
           public void onPageFinished(WebView view, String url) {
               super.onPageFinished(view, url);
           }

       });

       mWebVie.setWebChromeClient(new WebChromeClient() {
           @Override//实现js中的alert弹窗在Activity中显示
           public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
                 //result.confirm();
                 //return true;
               AlertDialog.Builder b = new AlertDialog.Builder(WebViewActivity.this);
               b.setTitle("Alert");
               b.setMessage(message);
               b.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       result.confirm();
                   }
               });
               b.setCancelable(false);
               b.create().show();
               return true;
           }

           @Override
           public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
               // 根据协议的参数，判断是否是所需要的url(原理同方式2)
               // 一般根据scheme（协议格式） & authority（协议名）判断（前两个参数）
               //假定传入进来的 url = "js://webview?arg1=111&arg2=222"（同时也是约定好的需要拦截的）
               Log.v("WebViewActivity","kevin onJsPrompt.message=="+message);
               Uri uri = Uri.parse(message);
               // 如果url的协议 = 预先约定的 js 协议
               // 就解析往下解析参数
               if ( uri.getScheme().equals("js")) {

                   // 如果 authority  = 预先约定协议里的 webview，即代表都符合约定的协议
                   // 所以拦截url,下面JS开始调用Android需要的方法
                   if (uri.getAuthority().equals("webview")|| uri.getAuthority().equals("demo")) {
                       //
                       // 执行JS所需要调用的逻辑
                       System.out.println("js调用了Android的方法");

                       // 可以在协议上带有参数并传递到Android上
                       HashMap<String, String> params = new HashMap<>();
                       Set<String> collection = uri.getQueryParameterNames();

                       for (String string :collection){
                           String value = uri.getQueryParameter(string);
                           Log.v("WebViewActivity","kevin onJsPrompt222 string="+string+" value=="+value);
                       }
                       //参数result:代表消息框的返回值(输入值)
                       result.confirm("js调用了Android的方法成功啦");
                   }
                   return true;
               }
               return super.onJsPrompt(view, url, message, defaultValue, result);
           }

           @Override
           public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
               return super.onJsConfirm(view, url, message, result);
           }
       });

       mWebVie.loadUrl("file:///android_asset/index.html");
       final Handler mHandler = new Handler();
//webview增加javascript接口，监听html页面中的js点击事件

           mWebVie.addJavascriptInterface(new Object() {
               @JavascriptInterface
               public String clickOnAndroid() {//将被js调用
                   mHandler.post(new Runnable() {
                       public void run() {
                           fromTakePhoto = true;
                          //调用 启用摄像头的自定义方法
                           takePhoto("testimg" + Math.random() * 1000 + 1 + ".jpg");
                           System.out.println("========fileFullName: " + fileFullName);

                       }

                   });
                   return fileFullName;
               }
           }, "demo");
       }


    /*
    * 调用摄像头的方法
    */
    public void takePhoto(String filename) {
        System.out.println("----start to take photo2 ----");
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_MEDIA_TITLE, "TakePhoto");

        //判断是否有SD卡
        String sdDir = null;
        boolean isSDcardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if(isSDcardExist) {
            sdDir = Environment.getExternalStorageDirectory().getAbsolutePath();
        } else {
            sdDir = Environment.getRootDirectory().getAbsolutePath();
        }
//确定相片保存路径
        String targetDir = sdDir + "/" + "webview_camera";
        File file = new File(targetDir);
        if (!file.exists()) {
            file.mkdirs();
        }
        fileFullName = targetDir + "/" + filename;
        System.out.println("----taking photo fileFullName: " + fileFullName);
//初始化并调用摄像头
        intent.putExtra(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(fileFullName)));
        startActivityForResult(intent, 1);
    }

    /*
    * (non-Javadoc)
    * @see android.app.Activity#onActivityResult(int, int, android.content.Intent)
    * 重写些方法，判断是否从摄像Activity返回的webview activity
    */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println("----requestCode: " + requestCode + "; resultCode " + resultCode + "; fileFullName: " + fileFullName);
        if (fromTakePhoto && requestCode ==1 && resultCode ==-1) {
            mWebVie.loadUrl("javascript:wave2('" + fileFullName + "')");
        } else {
            mWebVie.loadUrl("javascript:wave2('Please take your photo')");
        }
        fromTakePhoto = false;
        super.onActivityResult(requestCode, resultCode, data);
    }



}
