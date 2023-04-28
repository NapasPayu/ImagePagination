package com.napas.imagepagination.imagelist.adapter

import androidx.recyclerview.widget.DiffUtil
import com.napas.imagepagination.data.model.Image

object ImageComparator : DiffUtil.ItemCallback<Image>() {
    override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean {
        return oldItem == newItem
    }
}