package com.example.ez_tour

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.viewholder.view.*


class RecyclerAdapter(private val items: ArrayList<RecycleData>,context: Context) :
    RecyclerView.Adapter<RecyclerAdapter.ViewHolder>(), Filterable {

    override fun getItemCount() = items.size


    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {
        val item = items[position]
        val listener = View.OnClickListener {it ->
            val intent = Intent(holder.itemView.context,InformationActivity::class.java)
            intent.putExtra("name",holder.itemView.text_rename.toString())
            intent.putExtra("tag",holder.itemView.view_retag.toString())
            ContextCompat.startActivity(holder.itemView.context,intent,null)

        }
        holder.apply {
            bind(listener, item)
            itemView.tag = item
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            RecyclerAdapter.ViewHolder {
        val inflatedView = LayoutInflater.from(parent.context).inflate(R.layout.viewholder, parent, false)
        return RecyclerAdapter.ViewHolder(inflatedView)
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        private var view: View = v
        fun bind(listener: View.OnClickListener, item: RecycleData) {
            //view.view_reimage.setImageBitmap(item.imageURL)
            when(item.imageTag) {
                "카페" -> view.view_retag.setImageResource(R.drawable.kakao_login_large_narrow)
                "숙소" -> view.view_retag.setImageResource(R.drawable.kakao_login_large_narrow)
                "관광명소" -> view.view_retag.setImageResource(R.drawable.kakao_login_large_narrow)
                "음식점" -> view.view_retag.setImageResource(R.drawable.kakao_login_large_narrow)
            }
            view.text_rename.text = item.strName
            view.setOnClickListener(listener)
        }

    }

    override fun getFilter(): Filter {
        TODO("Not yet implemented")
    }
}