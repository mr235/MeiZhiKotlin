package com.mr235.meizhi_kotlin

import android.content.Context
import com.mr235.meizhi_kotlin.model.FuliData
import java.net.URL
import java.net.URLEncoder

/**
 * Created by Administrator on 2017/11/14.
 */
object Request {
    private val baseUrl = "http://gank.io/api/"
    private val fuliUrl = baseUrl + "data/福利/"
    private val videoUrl = baseUrl + "data/休息视频/"

    fun getFuliData(context: Context, listener: RequestListener<List<FuliData>>, count: Int = 10, page: Int = 1){
        val url = "$fuliUrl$count/$page"
        Util.getRequestQueue(context).add(BaseRequest(url, listener))
    }

    fun getVideoData(context: Context, listener: RequestListener<List<FuliData>>, count: Int = 10, page: Int = 1) {
        val url = "$videoUrl$count/$page"
        Util.getRequestQueue(context).add(BaseRequest(url, listener))
    }

}