package com.mr235.meizhi_kotlin

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.mr235.meizhi_kotlin.model.FuliData
import com.mr235.meizhi_kotlin.model.GankData
import java.text.SimpleDateFormat
import java.util.*

class GankActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    val adapter = MyAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gank)
        recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        val gankData = intent.getSerializableExtra(INTENT_DATA) as FuliData
        val date = gankData.publishedAt

        val format = SimpleDateFormat("yyyy/MM/dd")
        val toolbar = findViewById<Toolbar>(R.id.toolBar)
        toolbar.setTitle(format.format(date))
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        getData(date!!)
    }

    private fun getData(date: Date) {
        Request.getGankData(this, object : RequestListener<GankData>() {
            override fun success(data: GankData) {
                if (data != null) {
                    var elements = data.休息视频
                    if (elements != null) {
                        adapter.data.addAll(elements)
                    }
                    elements = data.Android
                    if (elements != null) {
                        adapter.data.addAll(elements)
                    }
                    elements = data.iOS
                    if (elements != null) {
                        adapter.data.addAll(elements)
                    }
                    elements = data.App
                    if (elements != null) {
                        adapter.data.addAll(elements)
                    }
                    elements = data.拓展资源
                    if (elements != null) {
                        adapter.data.addAll(elements)
                    }
                    elements = data.瞎推荐
                    if (elements != null) {
                        adapter.data.addAll(elements)
                    }
                    adapter.notifyDataSetChanged()
                }
            }

            override fun fail(message: String?) {
            }
        }, date!!.year + 1900, date!!.month + 1, date!!.date)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    inner class MyAdapter : RecyclerView.Adapter<MyAdapter.ViewHolder>() {
        val data = ArrayList<FuliData>()

        val onClickListener: View.OnClickListener = View.OnClickListener { v ->
            val fuliData = v.getTag(R.layout.item_gank) as FuliData
            val context = v.context
            val intent = Intent(context, WebViewActivity::class.java)
            intent.putExtra(INTENT_URL, fuliData.url)
            intent.putExtra(INTENT_TITLE, fuliData.desc)
            context.startActivity(intent)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            if (position == 0) {
                holder.tv_category.visibility = View.VISIBLE
                holder.tv_category.setText(data[position].type)
            } else {
                if (data[position].type == data[position-1].type) {
                    holder.tv_category.visibility = View.GONE
                } else {
                    holder.tv_category.visibility = View.VISIBLE
                    holder.tv_category.setText(data[position].type)
                }
            }
            holder.tv_title.setText("${data[position].desc}${if (data[position].who == null) "" else " (by  ${data[position].who})"}")
            holder.tv_title.setTag(R.layout.item_gank, data[position])

            holder.tv_title.setOnClickListener(onClickListener)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val v = LayoutInflater.from(parent.context).inflate(R.layout.item_gank, parent, false)
            var vh = ViewHolder(v);
            return vh;
        }

        override fun getItemCount(): Int {
            return data.size
        }

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val tv_category: TextView
            val tv_title: TextView
            val ll_title: View
            init {
                tv_category = itemView.findViewById(R.id.tv_category)
                tv_title = itemView.findViewById(R.id.tv_title)
                ll_title = itemView.findViewById(R.id.ll_title)
            }
        }
    }
}
