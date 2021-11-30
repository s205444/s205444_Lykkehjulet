package com.example.s205444_lykkehjulet.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.s205444_lykkehjulet.Fragments.LoseFragment
import com.example.s205444_lykkehjulet.R
import com.example.s205444_lykkehjulet.data.Categories


/**
 * @author Lucas Loft Skals
 * Inspiration from https://developer.android.com/jetpack/androidx/releases/recyclerview
 */
class ItemAdapter(
    private val context: LoseFragment,
    private val dataset: List<Categories>
) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {


    class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.item_title)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {

        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)

        return ItemViewHolder(adapterLayout)
    }


    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset[position]
        holder.textView.text = context.resources.getString(item.stringResourceId)
    }


    override fun getItemCount() = dataset.size
}