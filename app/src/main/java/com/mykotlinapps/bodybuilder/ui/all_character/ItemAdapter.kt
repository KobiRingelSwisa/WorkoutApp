package com.mykotlinapps.bodybuilder.ui.all_character

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mykotlinapps.bodybuilder.data.Item
import com.mykotlinapps.bodybuilder.databinding.ItemLayoutBinding

class ItemAdapter(val items:List<Item>, val callback: ItemListener): RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {
    interface ItemListener {
        fun onItemClicked(index: Int)
        fun onItemLongClicked(index: Int)
    }
    inner class ItemViewHolder(private val binding: ItemLayoutBinding) :RecyclerView.ViewHolder(binding.root), View.OnClickListener,View.OnLongClickListener{
        init {
            binding.root.setOnClickListener(this)
            binding.root.setOnLongClickListener(this)
        }

        override fun onClick(v: View?) {
            callback.onItemClicked(adapterPosition)
        }

        override fun onLongClick(v: View?): Boolean {
            callback.onItemLongClicked(adapterPosition)
            return true
        }

        fun bind(item: Item) {
            //TODO: Load the image
            binding.itemTitle.text = item.title
            binding.itemDescription.text = item.description
            //binding.itemImage.setImageURI(Uri.parse(item.photo))
            Glide.with(binding.root).load(item.photo)
                .circleCrop().into(binding.itemImage)       }
    }
    fun itemAt(position: Int)= items[position]
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)= ItemViewHolder(ItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) = holder.bind(items[position])
    override fun getItemCount() = items.size

}