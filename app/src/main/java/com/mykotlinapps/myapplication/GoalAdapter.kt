package com.mykotlinapps.myapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mykotlinapps.myapplication.databinding.GoalItemBinding

class GoalAdapter : RecyclerView.Adapter<GoalAdapter.GoalViewHolder>() {

    private var goals: List<Goal> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoalViewHolder {
        val binding = GoalItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GoalViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GoalViewHolder, position: Int) {
        holder.bind(goals[position])
    }

    override fun getItemCount(): Int = goals.size

    fun submitList(goalList: List<Goal>) {
        goals = goalList
        notifyDataSetChanged()
    }

    class GoalViewHolder(private val binding: GoalItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(goal: Goal) {
            binding.goalTitle.text = goal.title
            binding.goalDescription.text = goal.description
            binding.goalDuration.text = goal.duration.toString()
            // Add more bindings if needed
        }
    }
}
