package com.mykotlinapps.bodybuilder.data

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mykotlinapps.bodybuilder.databinding.FragmentWorkoutPlanBinding

data class Plan(val name: String, val description: String)

class PlansAdapter(private val plans: List<Plan>) : RecyclerView.Adapter<PlansAdapter.PlanViewHolder>() {

    class PlanViewHolder(private val binding: FragmentWorkoutPlanBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(plan: Plan) {
            binding.titleText.text = plan.name

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanViewHolder {
        val binding = FragmentWorkoutPlanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlanViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlanViewHolder, position: Int) {
        val plan = plans[position]
        holder.bind(plan)
    }

    override fun getItemCount(): Int {
        return plans.size
    }
}
