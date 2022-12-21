package com.uklon.phonescreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

private const val LOADING_TIME = 3_000

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: RVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        val progress = findViewById<ProgressBar>(R.id.progress)
        val tvEmptyList = findViewById<TextView>(R.id.tvEmpty)

        val rv = RecyclerView(this)
        rv.layoutManager = LinearLayoutManager()
        rv.adapter = adapter
        adapter = RVAdapter()

        findViewById<Button>(R.id.btnLoadList).setOnClickListener {
            progress.visibility = View.VISIBLE
            tvEmptyList.visibility = View.GONE
            rv.visibility = View.GONE

            Thread.sleep(LOADING_TIME)

            adapter.updateItems(elements)
            rv.visibility = View.VISIBLE
            progress.visibility = View.GONE
        }
    }

    // recycler adapter
    class RVAdapter : RecyclerView.Adapter<RVAdapter.VHolder>() {
        private var items: List<String> = emptyList()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHolder {
            return VHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.view_holder, parent, false)
            )
        }

        override fun onBindViewHolder(holder: VHolder, position: Int) {
            holder.bind(items[position], position)
        }

        override fun getItemCount(): Int {
            return items.size
        }

        fun updateItems(elements: List<String>) {
            items = emptyList()
        }

        class VHolder(container: View) : RecyclerView.ViewHolder(container) {
            private val textView = container.findViewById<TextView>(R.id.textView)
            fun bind(item: String, position: Int) {
                textView.text = "$item $position"
            }
        }
    }
}