package com.facundocetraro.deptchallenge.ui.dateList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.facundocetraro.deptchallenge.data.ImageDate
import com.facundocetraro.deptchallenge.databinding.DateItemBinding

class DateListAdapter() :
    ListAdapter<ImageDate, DateListAdapter.DateHolder>(ImageDateDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DateHolder {
        return DateHolder(
            DateItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    class DateHolder(private val dateItemBinding: DateItemBinding) : RecyclerView.ViewHolder(dateItemBinding.root) {
        fun bind(imageDate: ImageDate) {
            dateItemBinding.textView.text = imageDate.date
        }
    }

    override fun onBindViewHolder(holder: DateHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

object ImageDateDiffCallback : DiffUtil.ItemCallback<ImageDate>() {
    override fun areItemsTheSame(oldItem: ImageDate, newItem: ImageDate): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ImageDate, newItem: ImageDate): Boolean {
        return oldItem.date == newItem.date
    }
}