package com.facundocetraro.deptchallenge.ui.dateList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.facundocetraro.deptchallenge.R
import com.facundocetraro.deptchallenge.data.model.DateWithPhotos
import com.facundocetraro.deptchallenge.databinding.DateItemBinding

class DateListAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<DateWithPhotos, DateListAdapter.DateHolder>(DateWithPhotosDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DateHolder {
        return DateHolder(
            DateItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    class DateHolder(private val dateItemBinding: DateItemBinding) :
        RecyclerView.ViewHolder(dateItemBinding.root) {
        fun bind(dateWithPhotos: DateWithPhotos) {
            dateItemBinding.textView.text = dateWithPhotos.imageDate.date
            val savedImagesCount = dateWithPhotos.photoLists.count { it.localUri != null }

            val photosCount = dateWithPhotos.photoLists.size
            if (photosCount == 0) {
                dateItemBinding.downloadStatus.visibility = View.VISIBLE
                dateItemBinding.progress.visibility = View.GONE
                dateItemBinding.downloadStatus.load(R.drawable.ic_baseline_radio_button_unchecked_24)
            } else {
                when (savedImagesCount) {
                    photosCount -> {
                        dateItemBinding.downloadStatus.visibility = View.VISIBLE
                        dateItemBinding.progress.visibility = View.GONE
                        dateItemBinding.downloadStatus.load(R.drawable.ic_baseline_check_24)
                    }
                    0 -> {
                        dateItemBinding.downloadStatus.visibility = View.VISIBLE
                        dateItemBinding.progress.visibility = View.GONE
                        dateItemBinding.downloadStatus.load(R.drawable.ic_baseline_radio_button_unchecked_24)
                    }
                    else -> {
                        dateItemBinding.downloadStatus.visibility = View.GONE
                        dateItemBinding.progress.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    override fun onBindViewHolder(holder: DateHolder, position: Int) {
        val photo = getItem(position)
        holder.bind(photo)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(photo)
        }
    }

    class OnClickListener(val clickListener: (DateWithPhotos: DateWithPhotos) -> Unit) {
        fun onClick(DateWithPhotos: DateWithPhotos) = clickListener(DateWithPhotos)
    }
}

object DateWithPhotosDiffCallback : DiffUtil.ItemCallback<DateWithPhotos>() {
    override fun areItemsTheSame(oldItem: DateWithPhotos, newItem: DateWithPhotos): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: DateWithPhotos, newItem: DateWithPhotos): Boolean {
        return oldItem.imageDate.date == newItem.imageDate.date
    }
}
