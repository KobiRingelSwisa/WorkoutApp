package com.mykotlinapps.bodybuilder.data.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mykotlinapps.bodybuilder.R
import com.mykotlinapps.bodybuilder.data.Workout
import java.text.SimpleDateFormat
import java.util.Locale

class RecentSessionsAdapter(private val sessions: List<Workout>) :
    RecyclerView.Adapter<RecentSessionsAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.findViewById(R.id.sessionNameTextView)
        val durationTextView: TextView = view.findViewById(R.id.sessionDurationTextView)
        val dateTextView: TextView = view.findViewById(R.id.sessionDateTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recent_session, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val session = sessions[position]
        holder.nameTextView.text = session.name
        holder.durationTextView.text = session.duration
        holder.dateTextView.text = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(session.date)
    }

    override fun getItemCount() = sessions.size
}