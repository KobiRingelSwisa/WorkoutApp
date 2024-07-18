package com.mykotlinapps.bodybuilder.ui.all_character

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mykotlinapps.bodybuilder.data.Item
import com.mykotlinapps.bodybuilder.databinding.ItemLayoutBinding

class ItemAdapter(val items: List<Item>, val callback: ItemListener) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    interface ItemListener {
        fun onItemClicked(index: Int)
        fun onItemLongClicked(index: Int)
    }

    inner class ItemViewHolder(private val binding: ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root), View.OnClickListener, View.OnLongClickListener {

        init {
            binding.root.setOnClickListener(this)
            binding.root.setOnLongClickListener(this)
            binding.buttonTrack.setOnClickListener {
                toggleRecyclerViewVisibility()
            }
        }

        override fun onClick(v: View?) {
            callback.onItemClicked(adapterPosition)
        }

        override fun onLongClick(v: View?): Boolean {
            callback.onItemLongClicked(adapterPosition)
            return true
        }

        fun bind(item: Item) {
            // Load the image using Glide
            Glide.with(binding.root.context).load(item.gifUrl).into(binding.itemImage)

            // Set the item details
            binding.itemName.text = item.name
            binding.itemBodyPart.text = item.bodyPart
            binding.itemTarget.text = item.target
            binding.itemEquipment.text = item.equipment

            // Initially hide the RecyclerView
            binding.recyclerView.visibility = View.GONE
        }

        private fun toggleRecyclerViewVisibility() {
            binding.recyclerView.visibility = if (binding.recyclerView.visibility == View.GONE) View.VISIBLE else View.GONE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    fun itemAt(position: Int) = items[position]
}
