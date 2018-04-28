package com.example.kai.testwebview.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import service.LocalService;
import service.RemoteService;

/**
 * Created by kai on 2018/1/26.
 */

public class RestartServiceReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String msg = intent.getStringExtra("msg");
        Log.v("kevin","msg=="+msg);
        if(!TextUtils.isEmpty(msg)  && msg.equals("restart")){
            context.startService(new Intent(context,LocalService.class));
            context.startService(new Intent(context,RemoteService.class));
        }
    }
}
