package com.mr235.meizhi_kotlin


import android.content.Context
import android.graphics.Point
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import android.opengl.ETC1.getHeight
import android.opengl.ETC1.getWidth
import android.view.WindowManager



/**
 * Created by Administrator on 2017/11/14.
 */
object Util {
    private var queue: RequestQueue? = null
    fun getRequestQueue(context: Context): RequestQueue {
        if (queue == null) {
            queue = Volley.newRequestQueue(context.applicationContext)
        }
        return queue!!
    }

    fun getScreenSize(context: Context) : Point {
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager

        val point = Point()
        wm.defaultDisplay.getSize(point)
        return point
    }
}
