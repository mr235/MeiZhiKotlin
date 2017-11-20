package com.mr235.meizhi_kotlin

import android.os.Bundle
import android.support.v4.view.ViewCompat
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.mr235.meizhi_kotlin.model.FuliData

class BigPictureActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_big_picture)
        val imageView = findViewById<ImageView>(R.id.iv)
        val fuliData : FuliData = intent.getSerializableExtra(INTENT_DATA) as FuliData
        Glide.with(this).load(fuliData.url).into(imageView)
        ViewCompat.setTransitionName(imageView, TRANSITION_PIC)
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
