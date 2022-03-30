package com.cvs.find_flicker.ui.fragment.photos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cvs.find_flicker.R
import com.cvs.find_flicker.data.models.Photo
import com.cvs.find_flicker.databinding.ItemPhotoBinding
import com.squareup.picasso.Picasso

class PhotosAdapter: ListAdapter<Photo, RecyclerView.ViewHolder>(PhotoDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PhotoViewHolder(ItemPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as PhotoViewHolder).bind(getItem(position))
    }

    class PhotoViewHolder(
        private val binding: ItemPhotoBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        private lateinit var photo: Photo
        init {
            binding.imageView.setOnClickListener {
                navigateToDetail(photo, binding.imageView)
            }
        }

        private fun navigateToDetail(photo: Photo, view: View) {
            val direction = PhotosFragmentDirections.actionPhotoListToPhotoDetails(photo)
            view.findNavController().navigate(direction)
        }

        fun bind(item: Photo) {
            photo = item
            binding.title.text = item.title
            Picasso.get()
                .load(item.media.m)
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
                .into(binding.imageView)
        }
    }

}

private class PhotoDiffCallback: DiffUtil.ItemCallback<Photo>() {
    override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
        return oldItem.link == newItem.link
    }

    override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
        return oldItem == newItem
    }
}