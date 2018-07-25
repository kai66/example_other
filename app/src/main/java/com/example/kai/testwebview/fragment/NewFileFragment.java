package com.example.kai.testwebview.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.kai.testwebview.R;

import java.io.File;
import java.util.List;

import cn.finalteam.galleryfinal.permission.EasyPermissions;

/**
 * Created by kai on 2018/6/1.
 */

public class NewFileFragment extends BaseFragment implements EasyPermissions.PermissionCallbacks{

    RecyclerView recyclerView;

    private final int REQUEST_CODE_FILE = 122;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_newfile,container,false);
       initView(view);
       return view;
    }

    private void initView(View view) {
        recyclerView = (RecyclerView)view.findViewById(R.id.recycle_view);
        view.findViewById(R.id.new_file).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (EasyPermissions.hasPermissions(_mActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    Uri tempUri;
                    File file = new File(Environment.getExternalStorageDirectory(), "/kaitest/");
                    if(Build.VERSION.SDK_INT>=24) { //判读版本是否在7.0以上
                        //参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致   参数3  共享的文件
                        tempUri = FileProvider.getUriForFile(_mActivity, _mActivity.getPackageName(), file);//添加这一句表示对目标应用临时授权该Uri所代表的文件
                    }else{
                        tempUri = Uri.fromFile(file);
                    }
                    if(!file.exists()){
                        try {
                            file.mkdirs();
                        }catch (Exception e){

                        }
                    }
                   // Log.v("TAG","kevin 1111 file.path="+file.getPath()+"  tempUri.path=="+tempUri.getPath());
                } else {
                    EasyPermissions.requestPermissions(NewFileFragment.this, "申请文件读写权限", REQUEST_CODE_FILE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                }
            }
        });
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
    }


    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults,NewFileFragment.this);
    }

    @Override
    public void onPermissionsGranted(List<String> perms) {
        if(perms != null && perms.contains(Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            Uri tempUri;
            File file = new File(Environment.getExternalStorageDirectory(), "/kaitest");
            if(Build.VERSION.SDK_INT>=24) { //判读版本是否在7.0以上
                //参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致   参数3  共享的文件
                tempUri = FileProvider.getUriForFile(_mActivity, "com.example.kai.testwebview", file);//添加这一句表示对目标应用临时授权该Uri所代表的文件
            }else{
                tempUri = Uri.fromFile(file);
            }
            Log.v("TAG","kevin file.path="+file.getPath()+"  tempUri.path=="+tempUri.getPath());
        }
    }

    @Override
    public void onPermissionsDenied(List<String> perms) {

    }
}
