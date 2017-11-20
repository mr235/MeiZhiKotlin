package com.mr235.meizhi_kotlin

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView

/**
 * Created by Administrator on 2017/11/20.
 */
class SquareImageView : ImageView {
    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
    }
}