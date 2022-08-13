package com.facundocetraro.deptchallenge.ui.photoList

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.load
import com.facundocetraro.deptchallenge.data.model.Photo
import com.facundocetraro.deptchallenge.databinding.PhotoItemBinding
import java.io.File

class PhotoListAdapter :
    ListAdapter<Photo, PhotoListAdapter.PhotoHolder>(PhotoDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoHolder {
        return PhotoHolder(
            PhotoItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    class PhotoHolder(private val photoItemBinding: PhotoItemBinding) :
        RecyclerView.ViewHolder(photoItemBinding.root) {
        fun bind(photo: Photo) {
            if (photo.localUri != null) {
                photoItemBinding.imageView.load(File(photo.localUri!!))
                photoItemBinding.photoProgress.visibility = View.GONE
                Log.d("Loaded data:", layoutPosition.toString() + "-" + photo.localUri!!)
            } else {
                photoItemBinding.imageView.setImageDrawable(null)
                photoItemBinding.photoProgress.visibility = View.VISIBLE
                Log.d("Loaded data", "$layoutPosition- con valor nulo")
            }
        }
    }

    override fun onBindViewHolder(holder: PhotoHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

object PhotoDiffCallback : DiffUtil.ItemCallback<Photo>() {
    override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
        return oldItem.identifier == newItem.identifier
    }
}