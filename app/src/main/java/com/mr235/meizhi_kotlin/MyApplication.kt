package com.mr235.meizhi_kotlin

import android.app.Application
import android.graphics.Bitmap
import android.webkit.CookieManager
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache
import com.nostra13.universalimageloader.core.DisplayImageOptions
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer

/**
 * Created by Administrator on 2017/11/20.
 */
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        CookieManager.getInstance().setAcceptCookie(true)

        val displayImageOptions = DisplayImageOptions.Builder()
                .showImageForEmptyUri(android.R.color.transparent)
                .showImageOnFail(android.R.color.transparent)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .cacheInMemory(true) // default is false
                .resetViewBeforeLoading(true)
                .cacheOnDisk(true)// default is false
                .displayer(FadeInBitmapDisplayer(200))
                .build()

        var config = ImageLoaderConfiguration.Builder(this)
                .threadPoolSize(5)
                .memoryCache(WeakMemoryCache())
                .memoryCacheSize(10 * 1024 * 1024)
                .diskCacheFileNameGenerator(Md5FileNameGenerator())
                .diskCacheSize(50 * 1024 * 1024)
                .defaultDisplayImageOptions(displayImageOptions)
                .build()
        ImageLoader.getInstance().init(config)
    }
}