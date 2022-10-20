package com.example.catfacts.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.catfacts.R
import com.example.catfacts.model.CatDetailModel
import kotlinx.android.synthetic.main.cat_item_view.view.*

class CatBreedsAdapter() : RecyclerView.Adapter<CatBreedsAdapter.ListViewHolder>() {

    // Cat Breeds list
    var list: List<CatDetailModel> = ArrayList()

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.cat_item_view,
                parent,
                false
            )
        )
    }

    //Click listener for recyclerview item
    private var onItemClickListener: ((CatDetailModel) -> Unit)? = null

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {

        holder.itemView.apply {
            //Set cat breed name
            tv_catName.text = list[position].breed
            setOnClickListener {
                onItemClickListener?.let { it(list[position]) }
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    //Click listener function to reach it from fragment
    fun setOnItemClickListener(listener: (CatDetailModel) -> Unit) {
        onItemClickListener = listener
    }
}