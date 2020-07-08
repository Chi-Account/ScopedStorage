package com.chi.scopedstorage

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class PicturesAdapter(
    val uriList: List<Uri>,
    val context: Context,
    val imageSize: Int,
    val onItemClickListener: OnItemClickListener
) :
    RecyclerView.Adapter<PicturesAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView = itemView.findViewById<ImageView>(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_pictures,
            parent,
            false
        )
        val viewHolder = ViewHolder(view)
        view.setOnClickListener {
            onItemClickListener.onItemClick(viewHolder.adapterPosition)
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.imageView.layoutParams.width = imageSize
        holder.imageView.layoutParams.height = imageSize
        Glide.with(context).load(uriList[position]).into(holder.imageView)
    }

    override fun getItemCount() = uriList.size

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
}