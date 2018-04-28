package service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.kai.testwebview.IAlarmInterface;
import com.example.kai.testwebview.alarm.AlarmManagerUtil;

/**
 * Created by kai on 2018/1/25.
 */

public class RemoteService extends Service{

    private AlarmStub alarmStub;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        if(alarmStub ==  null){
            alarmStub = new AlarmStub();
        }
        return alarmStub;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if(alarmStub ==  null){
            alarmStub = new AlarmStub();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.v("TAG","kevin RemoteService.onStartCommand");
        RemoteService.this.bindService(new Intent(RemoteService.this,LocalService.class),connection, Context.BIND_ABOVE_CLIENT);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.v("kevin","RemoteService.onDestroy");
    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.v("TAG","kevin RemoteService onServiceConnected");
            if(alarmStub!=null){
                try {
                    alarmStub.setAlarm("remote");
                }catch (Exception e){

                }
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.v("TAG","kevin RemoteService onServiceDisconnected");
            //Intent remoteIntent = new Intent(RemoteService.this,LocalService.class);
            //RemoteService.this.startService(remoteIntent);
            //RemoteService.this.bindService(new Intent(RemoteService.this,LocalService.class),connection, Context.BIND_ABOVE_CLIENT);
            Intent intent = new  Intent("com.youlan.alarmset.service.restart");
            //intent.setAction("com.alarm.service.restart");
            intent.putExtra("msg","restart");
            intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
            Log.v("kevin","sendBroadcast");
            sendBroadcast(intent);
        }
    };

    public class AlarmStub extends IAlarmInterface.Stub{
        @Override
        public void setAlarm(String time) throws RemoteException {
            Log.v("RemoteService","kevin RemoteService.setAlarm.time=="+time);
            AlarmManagerUtil.setAlarm(RemoteService.this, 0, 14, 32
                   , 0, 0, "闹钟响了", 1);
        }
    }


}
