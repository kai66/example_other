package service;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.kai.testwebview.IAlarmInterface;

/**
 * Created by kai on 2018/1/25.
 */

public class LocalService extends Service{

    private AlarmBinder alarmBinder;

    @Override
    public void onCreate() {
        super.onCreate();
        if(alarmBinder == null){
            alarmBinder = new AlarmBinder();
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        if(alarmBinder == null){
            alarmBinder = new AlarmBinder();
        }
        return alarmBinder;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.v("TAG","kevin LocalService.onStartCommand");
        LocalService.this.bindService(new Intent(LocalService.this,RemoteService.class),connection, Context.BIND_ABOVE_CLIENT);
        return super.onStartCommand(intent, flags, startId);
    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.v("TAG","kevin LocalService onServiceConnected");
            if(alarmBinder != null){
                try {
                    alarmBinder.setAlarm("local");
                }catch (Exception e){

                }
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
                 Log.v("TAG","kevin LocalService onServiceDisconnected");
                Intent localIntent = new Intent(LocalService.this,RemoteService.class);
                LocalService.this.startService(localIntent);
                LocalService.this.bindService(new Intent(LocalService.this,RemoteService.class),connection, Context.BIND_ABOVE_CLIENT);
        }
    };

    public class AlarmBinder extends IAlarmInterface.Stub{
        @Override
        public void setAlarm(String time) throws RemoteException {
            Log.v("LocalService","kevin LocalService.setAlarm.time=="+time);
        }
    }


}
