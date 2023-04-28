package com.napas.imagepagination.imagelist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.napas.imagepagination.data.model.Image
import com.napas.imagepagination.databinding.ItemImageBinding

class ImageListAdapter(
    private val onItemClicked: (Image) -> Unit
) : PagingDataAdapter<Image, ImageListAdapter.ViewHolder>(ImageComparator) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { image ->
            holder.bind(image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemImageBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    inner class ViewHolder(private val binding: ItemImageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private var currentImage: Image? = null

        init {
            binding.root.setOnClickListener {
                currentImage?.let(onItemClicked)
            }
        }

        fun bind(image: Image) {
            with(image) {
                currentImage = this
                binding.ivPhoto.load(imageUrl)
                binding.tvAuthor.text = author
            }
        }
    }
}