package com.example.chatappwithfirebase.ui.adapters

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.chatappwithfirebase.R
import com.example.chatappwithfirebase.data.local.LocalStorage
import com.example.chatappwithfirebase.data.models.GroupData
import com.example.chatappwithfirebase.data.models.MessageData
import com.example.chatappwithfirebase.databinding.ItemChatAnotherBinding
import com.example.chatappwithfirebase.databinding.ItemChatMeBinding
import com.example.chatappwithfirebase.databinding.ItemGroupBinding
import com.example.chatappwithfirebase.utils.toTime
import kotlin.random.Random

class ChatAdapter : ListAdapter<MessageData, RecyclerView.ViewHolder>(diffCall) {

    companion object {
        const val ME = 0
        const val ANOTHER = 1
    }

    inner class ChatAnotherViewHolder(private val binding: ItemChatAnotherBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            val d = getItem(absoluteAdapterPosition)
            binding.tvMessage.text = d.message
            binding.tvUsername.text = d.user
            binding.tvTime.text = d.time
        }
    }

    inner class ChatMeViewHolder(private val binding: ItemChatMeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            val d = getItem(absoluteAdapterPosition)
            binding.tvMessage.text = d.message
            binding.tvTime.text = d.time
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when (viewType) {
            ME -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_chat_me, parent, false)
                val binding = ItemChatMeBinding.bind(view)
                ChatMeViewHolder(binding)
            }
            else -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_chat_another, parent, false)
                val binding = ItemChatAnotherBinding.bind(view)
                ChatAnotherViewHolder(binding)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {

        return when (getItem(position).user) {
            LocalStorage().username -> ME
            else -> ANOTHER
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItem(position).user) {
            LocalStorage().username -> {
                (holder as ChatMeViewHolder).bind()
            }
            else -> {
                (holder as ChatAnotherViewHolder).bind()
            }
        }
    }


    private object diffCall : DiffUtil.ItemCallback<MessageData>() {
        override fun areItemsTheSame(oldItem: MessageData, newItem: MessageData): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: MessageData, newItem: MessageData): Boolean {
            return oldItem.message == newItem.message && oldItem.user == newItem.user
        }

    }
}