package com.mr235.meizhi_kotlin

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.mr235.meizhi_kotlin.model.FuliData
import com.nostra13.universalimageloader.core.ImageLoader
import java.util.*
import kotlin.collections.HashMap

class MainActivity : AppCompatActivity() {

    lateinit var mRecyclerView : RecyclerView
    lateinit var swipeRefreshLayout : SwipeRefreshLayout
    val adapter = MyAdapter()

    private val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Toolbar>(R.id.toolBar).setTitle(R.string.app_name)
        mRecyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        swipeRefreshLayout = findViewById<SwipeRefreshLayout>(R.id.srl)
        mRecyclerView.layoutManager = layoutManager
        mRecyclerView.adapter = adapter

        mRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val isBottom = layoutManager.findLastCompletelyVisibleItemPositions(IntArray(2))[1] >= adapter.itemCount - 4
                if (!swipeRefreshLayout.isRefreshing && isBottom) {
                    swipeRefreshLayout.isRefreshing = true
                    getData(page + 1)
                }
            }
        })

        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = true
            getData(1)
        }
        getData(1)
    }

    var page = 0
    private fun getData(page: Int) {
        this.page = page
        Request.getFuliData(this, object : RequestListener<List<FuliData>>() {
            override fun success(data: List<FuliData>) {
                if (data != null) {
                    if (page == 1) {
                        adapter.data.clear()
                    }
                    adapter.data.addAll(data)
                    adapter.notifyDataSetChanged()
                }
            }

            override fun fail(message: String?) {
            }

            override fun onFinish() {
                super.onFinish()
                swipeRefreshLayout.isRefreshing = false
            }

        }, page = page)

        Request.getVideoData(this, object : RequestListener<List<FuliData>>() {
            override fun success(data: List<FuliData>) {
                if (data != null) {
                    adapter.videoMap.putAll(data.map { it.publishedAt to it }.toMap())
                    adapter.notifyDataSetChanged()
                }
            }

            override fun fail(message: String?) {
            }
        }, page = page)
    }


    class MyAdapter : RecyclerView.Adapter<MyAdapter.ViewHolder>() {

        val data = ArrayList<FuliData>()
        private var screenWidth = 0

        val videoMap = HashMap<Date?, FuliData>()
        val imageLoader = ImageLoader.getInstance()

        override fun getItemCount(): Int {
            return data.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val fuliData = data[position]
            val videoInfo = videoMap.get(fuliData.publishedAt)
            holder.tv.text = "${fuliData.desc} ${if (videoInfo?.desc == null) "" else videoInfo?.desc}"

            if (screenWidth == 0) {
                screenWidth = Util.getScreenSize(holder.itemView.context).x
            }
//            imageLoader.displayImage("${fuliData.url}?imageView2/0/h/${screenWidth/2}", holder.iv)
            Glide.with(holder.iv).load("${fuliData.url}?imageView2/0/h/${screenWidth/2}").into(holder.iv)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

            val v = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
            var vh = ViewHolder(v);
            return vh;
        }

        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val tv : TextView
            val iv : ImageView
            init {
                tv = itemView.findViewById<TextView>(R.id.tv)
                iv = itemView.findViewById<ImageView>(R.id.iv)
            }
        }
    }
}

