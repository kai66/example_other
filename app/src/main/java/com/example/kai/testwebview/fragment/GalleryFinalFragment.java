package com.example.kai.testwebview.fragment;

import android.content.res.Resources;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.kai.testwebview.R;

import java.io.File;
import java.util.List;

import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.ThemeConfig;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import service.GlideImageLoader;
import service.GlidePauseOnScrollListener;

/**
 * Created by kai on 2018/5/29.
 */

public class GalleryFinalFragment extends BaseFragment{

    ImageView imgView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_gallery_final,container,false);
        initView(view);
        initFinalGallery();
        return view;
    }

   void initFinalGallery(){
       String YLDIR="/galleryfinal/img/Cache/";
       Resources Res = _mActivity.getResources();
       ThemeConfig theme = new ThemeConfig.Builder()
               .setTitleBarBgColor(Res.getColor(R.color.yellow))
               .setCheckNornalColor(Res.getColor(R.color.yellow))
               .setCheckSelectedColor(Res.getColor(R.color.orange))
               .setCropControlColor(Res.getColor(R.color.yellow))
               .setFabNornalColor(Res.getColor(R.color.yellow))
               .setFabPressedColor(Res.getColor(R.color.yellow))
               .build();
       CoreConfig coreConfig = new CoreConfig.Builder(_mActivity, new GlideImageLoader(), theme)
               .setTakePhotoFolder(new File(Environment.getExternalStorageDirectory() + YLDIR))
               .setEditPhotoCacheFolder(new File(Environment.getExternalStorageDirectory() + YLDIR))
               .setPauseOnScrollListener(new GlidePauseOnScrollListener(false, true))
               .build();
       GalleryFinal.init(coreConfig);
    }

    private void initView(View view){

        imgView = (ImageView)view.findViewById(R.id.img_view);

        view.findViewById(R.id.bt_camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FunctionConfig  functionConfig = new FunctionConfig.Builder()
                        .setEnableCamera(true)
                        .setEnableEdit(false)
                        .setEnableCrop(false)
                        .setEnableRotate(false)
                        .setCropSquare(false)
                        .setEnablePreview(false)
                        .setCropSquare(false)
                        .setForceCrop(false)
                        .build();
                GalleryFinal.openCamera(10000, functionConfig, new GalleryFinal.OnHanlderResultCallback() {
                    @Override
                    public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
                        if(resultList!=null){
                            Glide.with(_mActivity)
                                    .load("file://" + resultList.get(0).getPhotoPath())
                                    .error(R.mipmap.ic_launcher_round)
                                    .centerCrop()
                                    .into(imgView);
                        }
                    }

                    @Override
                    public void onHanlderFailure(int requestCode, String errorMsg) {

                    }
                });
            }
        });

        view.findViewById(R.id.bt_gallery).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FunctionConfig  functionConfig = new FunctionConfig.Builder()
                        .setEnableCamera(false)
                        .setEnableEdit(false)
                        .setEnableCrop(false)
                        .setEnableRotate(false)
                        .setCropSquare(false)
                        .setEnablePreview(false)
                        .setCropSquare(false)
                        .setForceCrop(false)
                        .build();
                GalleryFinal.openGallerySingle(10001, functionConfig, new GalleryFinal.OnHanlderResultCallback() {
                    @Override
                    public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
                        if(resultList!=null){
                            Glide.with(_mActivity)
                                    .load("file://" + resultList.get(0).getPhotoPath())
                                    .error(R.mipmap.ic_launcher_round)
                                    .centerCrop()
                                    .into(imgView);
                        }
                    }

                    @Override
                    public void onHanlderFailure(int requestCode, String errorMsg) {

                    }
                });

            }
        });

    }


}
