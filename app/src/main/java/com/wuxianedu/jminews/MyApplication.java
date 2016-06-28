package com.wuxianedu.jminews;

import android.app.Application;
import android.widget.ImageView;

import org.xutils.image.ImageOptions;
import org.xutils.x;

/**
 * Created by Hu131 on 2016/6/28.
 */
public class MyApplication extends Application {
    public static ImageOptions imageOptions;

    @Override
    public void onCreate() {
        super.onCreate();
        //xutils3框架的初始化
        x.Ext.init(this);

        imageOptions = new ImageOptions.Builder()
                // 如果ImageView的大小不是定义为wrap_content, 不要crop.
                .setCrop(true)
                // 加载中或错误图片的ScaleType
                //.setPlaceholderScaleType(ImageView.ScaleType.MATRIX)
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                //设置加载过程中的图片
                .setLoadingDrawableId(R.mipmap.ic_launcher)
                //设置加载失败后的图片
                .setFailureDrawableId(R.mipmap.ic_launcher)
                //设置使用缓存
                .setUseMemCache(true)
                //设置支持gif
//                .setIgnoreGif(false)
                //设置显示圆形图片
//      .setCircular(false)
                .build();


    }


}
