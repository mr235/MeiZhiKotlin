package com.mr235.meizhi_kotlin.model

import java.util.*

/**
 * Created by Administrator on 2017/11/18.
 */
class FuliData {
//    {
//        _id: "5a0e4a0d421aa90fe7253643",
//        createdAt: "2017-11-17T10:31:41.155Z",
//        desc: "11-17",
//        publishedAt: "2017-11-17T12:39:48.189Z",
//        source: "chrome",
//        type: "福利",
//        url: "http://7xi8d6.com1.z0.glb.clouddn.com/2017-11-17-22794158_128707347832045_9158114204975104000_n.jpg",
//        used: true,
//        who: "代码家"
//    },
    var _id : String = ""
        get() = if (_id == null) "" else _id

    var createdAt : String = ""
    var desc : String = ""
    var publishedAt : String = ""
    var source : String = ""
    var type : String = ""
    var url : String = ""
    var used : Boolean = false
    var who : String = ""
}