package com.edu.android_day1229_01_imagehttp;

import android.app.Application;
import android.graphics.Bitmap;

import com.edu.android_day1229_01_imagehttp.db.DBHelper;
import com.squareup.picasso.LruCache;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

/**
 * Created by Ming on 2015/12/29.
 */
public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Picasso picasso = new Picasso.Builder(this)
                .memoryCache(new LruCache(10 << 20))
                .downloader(new OkHttpDownloader(getCacheDir(), 30 << 20))
                .defaultBitmapConfig(Bitmap.Config.RGB_565)
                .build();

        // 设定之后 全部都生效
        Picasso.setSingletonInstance(picasso);
        DBHelper.setDatabase(new DBHelper(this));
    }
}
