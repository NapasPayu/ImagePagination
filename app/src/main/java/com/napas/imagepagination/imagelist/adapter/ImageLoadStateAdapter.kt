package com.napas.imagepagination.imagelist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.napas.imagepagination.R

class ImageLoadStateAdapter : LoadStateAdapter<ImageLoadStateAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ) = ViewHolder(parent)

    override fun onBindViewHolder(
        holder: ViewHolder,
        loadState: LoadState
    ) = holder.bind(loadState)

    inner class ViewHolder(
        parent: ViewGroup
    ) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_load_state, parent, false)
    ) {
        fun bind(loadState: LoadState) {
            // do nothing
        }
    }
}