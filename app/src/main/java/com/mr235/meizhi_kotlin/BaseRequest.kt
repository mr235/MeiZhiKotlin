package com.mr235.meizhi_kotlin

import android.text.TextUtils
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.google.gson.Gson
import org.json.JSONObject
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.net.URI
import java.net.URL


/**
 * Created by Administrator on 2017/11/18.
 */
class BaseRequest<T>(url: String, val listener: RequestListener<T>?) : StringRequest(encodeUrl(url), null, null) {

    override fun deliverResponse(response: String?) {
        if (listener != null) {
            if (!TextUtils.isEmpty(response)) {
                val jsonObject = JSONObject(response)
                if (jsonObject.has("results")) {
                    var type = getType()
                    val gson = Gson()
                    val t = gson.fromJson<T>(jsonObject.get("results").toString(), type)
                    listener.success(t)
                }
            } else {
                listener.fail("没有数据")
            }
        }
    }

    private fun getType(): Type {
        var type: Type = Object::class.java
        val genericInterfaces = listener!!::class.java.genericInterfaces
        if (genericInterfaces != null && genericInterfaces.size > 0) {
            if (genericInterfaces[0] is ParameterizedType) {
                type = (genericInterfaces[0] as ParameterizedType).actualTypeArguments[0]
            }
        }
        return type
    }

    override fun deliverError(error: VolleyError?) {
        if (listener != null) {
            listener?.fail(error?.message)
        }
    }
}

interface RequestListener <T> {
    fun success(data : T)
    fun fail(message: String?)
}

private fun encodeUrl(url: String?): String {
    if (!TextUtils.isEmpty(url)) {
        val url = URL(url)
        val uri = URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef())
        return uri.toString()
    }
    return ""
}