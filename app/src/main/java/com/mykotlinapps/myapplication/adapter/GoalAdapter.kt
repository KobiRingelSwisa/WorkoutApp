package com.mykotlinapps.myapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mykotlinapps.myapplication.data.Goal
import com.mykotlinapps.myapplication.databinding.GoalItemBinding

class GoalAdapter : ListAdapter<Goal, GoalAdapter.GoalViewHolder>(GoalDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoalViewHolder {
        val binding = GoalItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GoalViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GoalViewHolder, position: Int) {
        val goal = getItem(position)
        holder.bind(goal)
    }

    class GoalViewHolder(private val binding: GoalItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(goal: Goal) {
            binding.goalTitle.text = goal.title
            binding.goalDescription.text = goal.description
        }
    }

    class GoalDiffCallback : DiffUtil.ItemCallback<Goal>() {
        override fun areItemsTheSame(oldItem: Goal, newItem: Goal): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Goal, newItem: Goal): Boolean {
            return oldItem == newItem
        }
    }
}
