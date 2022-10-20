package com.example.catfacts.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.catfacts.R
import com.example.catfacts.model.CatFactModel
import kotlinx.android.synthetic.main.cat_fact_item_view.view.*

class CatFactAdapter() : RecyclerView.Adapter<CatFactAdapter.ListViewHolder>() {
    //Cat Facts list
    var list: List<CatFactModel> = ArrayList()

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.cat_fact_item_view,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {

        holder.itemView.apply {
            //Used Fact Name as title
            tv_factName.text = "Fact Name :\n" + list[position].fact
            tv_fact_length.text = "Length :" + list[position].length.toString()
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

}