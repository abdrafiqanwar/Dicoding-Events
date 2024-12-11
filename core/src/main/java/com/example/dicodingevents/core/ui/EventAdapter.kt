package com.example.dicodingevents.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dicodingevents.core.domain.model.Event
import com.example.dicodingevents.core.databinding.ItemEventBinding

class EventAdapter : ListAdapter<com.example.dicodingevents.core.domain.model.Event, EventAdapter.ListViewHolder>(DIFF_CALLBACK) {

    var onItemClick: ((com.example.dicodingevents.core.domain.model.Event) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ListViewHolder(
            ItemEventBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data)
    }

    inner class ListViewHolder(private var binding: ItemEventBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: com.example.dicodingevents.core.domain.model.Event) {
            Glide.with(itemView.context)
                .load(data.imageLogo)
                .into(binding.ivImage)
            binding.tvName.text = data.name
            binding.tvCategory.text = data.category
            binding.tvOwnerName.text = "oleh: ${data.ownerName}"
        }

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(getItem(bindingAdapterPosition))
            }
        }
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<com.example.dicodingevents.core.domain.model.Event> =
            object : DiffUtil.ItemCallback<com.example.dicodingevents.core.domain.model.Event>() {
                override fun areItemsTheSame(oldItem: com.example.dicodingevents.core.domain.model.Event, newItem: com.example.dicodingevents.core.domain.model.Event): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(oldItem: com.example.dicodingevents.core.domain.model.Event, newItem: com.example.dicodingevents.core.domain.model.Event): Boolean {
                    return oldItem == newItem
                }
            }
    }
}