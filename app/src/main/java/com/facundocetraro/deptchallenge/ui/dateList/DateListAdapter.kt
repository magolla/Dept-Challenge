package com.facundocetraro.deptchallenge.ui.dateList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.facundocetraro.deptchallenge.R
import com.facundocetraro.deptchallenge.data.model.DateWithSavedStatus
import com.facundocetraro.deptchallenge.data.model.DownloadState
import com.facundocetraro.deptchallenge.databinding.DateItemBinding

class DateListAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<DateWithSavedStatus, DateListAdapter.DateHolder>(DateWithSavedStatusDiffCallback) {

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
        fun bind(dateWithSavedStatus: DateWithSavedStatus) {
            dateItemBinding.textView.text = dateWithSavedStatus.imageDate

            when (dateWithSavedStatus.photoLists) {
                DownloadState.DOWNLOADED -> {
                    dateItemBinding.downloadStatus.visibility = View.VISIBLE
                    dateItemBinding.progress.visibility = View.GONE
                    dateItemBinding.downloadStatus.load(R.drawable.ic_baseline_check_24)
                }
                DownloadState.NOT_STARTED -> {
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

    override fun onBindViewHolder(holder: DateHolder, position: Int) {
        val photo = getItem(position)
        holder.bind(photo)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(photo)
        }
    }

    class OnClickListener(val clickListener: (DateWithSavedStatus: DateWithSavedStatus) -> Unit) {
        fun onClick(DateWithSavedStatus: DateWithSavedStatus) = clickListener(DateWithSavedStatus)
    }
}

object DateWithSavedStatusDiffCallback : DiffUtil.ItemCallback<DateWithSavedStatus>() {
    override fun areItemsTheSame(
        oldItem: DateWithSavedStatus,
        newItem: DateWithSavedStatus
    ): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: DateWithSavedStatus,
        newItem: DateWithSavedStatus
    ): Boolean {
        return oldItem.imageDate == newItem.imageDate
    }
}
