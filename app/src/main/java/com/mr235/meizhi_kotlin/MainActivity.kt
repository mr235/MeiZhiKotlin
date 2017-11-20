package com.mr235.meizhi_kotlin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.mr235.meizhi_kotlin.model.FuliData

class MainActivity : AppCompatActivity() {

    lateinit var queue : RequestQueue
    lateinit var mRecyclerView : RecyclerView
    val adapter = MyAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        queue = Volley.newRequestQueue(this)
        findViewById<Toolbar>(R.id.toolBar).setTitle(R.string.app_name)
        mRecyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mRecyclerView.adapter = adapter
        getData()
    }

    private fun getData() {
        mRecyclerView
        queue.add(BaseRequest(
                "http://gank.io/api/data/福利/15/1",
                object : RequestListener<List<FuliData>> {
                    override fun success(data: List<FuliData>) {
                        if (data != null) {
                            adapter.getData().addAll(data)
                            adapter.notifyDataSetChanged()
                        }
                    }

                    override fun fail(message: String?) {
                    }
                }
        ))
    }


    class MyAdapter : RecyclerView.Adapter<MyAdapter.ViewHolder>() {

        private var data = ArrayList<FuliData>()
        fun getData() : MutableList<FuliData> {
            return data
        }

        override fun getItemCount(): Int {
            println(data.size)
            return data.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            var gson = Gson()
            gson.toJson(data[position])
            holder.tv.text = data[position].desc
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

            val v = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
            var vh = ViewHolder(v);
            return vh;
        }

        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val tv : TextView
            init {
                tv = itemView.findViewById<TextView>(R.id.tv)
            }
        }
    }
}

