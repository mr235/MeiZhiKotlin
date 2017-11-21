package com.mr235.meizhi_kotlin

import android.content.Context
import com.mr235.meizhi_kotlin.model.FuliData
import com.mr235.meizhi_kotlin.model.GankData

/**
 * Created by Administrator on 2017/11/14.
 */
object Request {
    private val baseUrl = "http://gank.io/api/"
    private val fuliUrl = baseUrl + "data/福利/"
    private val videoUrl = baseUrl + "data/休息视频/"
    private val gankUrl = baseUrl + "day/"

    fun getFuliData(context: Context, listener: RequestListener<List<FuliData>>, count: Int = 15, page: Int = 1){
        val url = "$fuliUrl$count/$page"
        Util.getRequestQueue(context).add(BaseRequest(url, listener))
    }

    fun getVideoData(context: Context, listener: RequestListener<List<FuliData>>, count: Int = 15, page: Int = 1) {
        val url = "$videoUrl$count/$page"
        Util.getRequestQueue(context).add(BaseRequest(url, listener))
    }

    fun getGankData(context: Context, listener: RequestListener<GankData>, year: Int, month: Int, day: Int) {
        val url = "$gankUrl$year/$month/$day"
        Util.getRequestQueue(context).add(BaseRequest(url, listener))
    }
}