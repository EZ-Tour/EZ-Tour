package com.example.ez_tour

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.viewholder.view.*


class RecyclerAdapter(val items: MutableList<RecycleData>,val context: Context) :
    RecyclerView.Adapter<RecyclerAdapter.ViewHolder>(),Filterable{

    private var filteredList = mutableListOf<RecycleData>()

    init {
        filteredList = items
    }
    override fun getItemCount() = filteredList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context,InformationActivity::class.java)
            intent.putExtra("name",holder.itemView.text_rename.text.toString())
            intent.putExtra("tag",filteredList.get(position).imageTag)
            ContextCompat.startActivity(holder.itemView.context,intent,null)
        }
        val image = holder.itemView.findViewById<ImageView>(R.id.view_reimage)
        image.setImageBitmap(filteredList.get(position).bitmap)
        val name = holder.itemView.findViewById<TextView>(R.id.text_rename)
        name.text = "${filteredList.get(position).strName}" + ""
        val tag = holder.itemView.findViewById<ImageView>(R.id.view_retag)
        tag.setImageResource(
            when (filteredList.get(position).imageTag) {
                "카페" -> R.drawable.kakao_login_large_narrow
                "숙소" -> R.drawable.kakao_login_large_narrow
                "관광명소" -> R.drawable.kakao_login_large_narrow
                "음식점" -> R.drawable.kakao_login_large_narrow
                else -> R.drawable.kakao_login_large_narrow
            }
        )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflatedView =
            LayoutInflater.from(parent.context).inflate(R.layout.viewholder, parent, false)
        return ViewHolder(inflatedView)
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        private var view: View = v

    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charString: CharSequence?): FilterResults {
                var charSearch = charString.toString()
                if (charString!!.isEmpty()){
                    filteredList = items
                }else{
                    val resultList = mutableListOf<RecycleData>()
                    for (row in items){
                        if (row.strName?.toLowerCase()?.contains(charSearch.toLowerCase())!!){
                            resultList.add(row)
                        }
                    }
                    filteredList = resultList
                }

                val filterResults = FilterResults()
                filterResults.values = filteredList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence, results: FilterResults) {
                results.values?.let {
                    filteredList = results.values as MutableList<RecycleData>
                    notifyDataSetChanged()
                }
            }

        }
    }
}