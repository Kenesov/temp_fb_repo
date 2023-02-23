package com.example.chatappwithfirebase.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.chatappwithfirebase.app.App
import com.example.chatappwithfirebase.data.models.GroupData
import com.example.chatappwithfirebase.databinding.ItemGroupBinding
import kotlin.random.Random

class GroupAdapter : RecyclerView.Adapter<GroupAdapter.GroupViewHolder>() {

    inner class GroupViewHolder(private val binding: ItemGroupBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            val d = models[absoluteAdapterPosition]
            binding.tvGroupName.text = d.name
            binding.tvLastMessage.text = "${d.name}: Qalay aman saw ne qv? Ee boladi ozin ne"
        }

        init {

            binding.root.setOnClickListener {
                onItemClickListener?.invoke(models[absoluteAdapterPosition])
            }
        }
    }

    private var onItemClickListener: ((GroupData) -> Unit)? = null
    fun setOnItemClickListener(block: ((GroupData) -> Unit)) {
        onItemClickListener = block
    }


    var models = listOf<GroupData>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {

        return GroupViewHolder(
            ItemGroupBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount() = models.size

    override fun onBindViewHolder(holder: GroupViewHolder, position: Int) {
        holder.bind()
    }
}