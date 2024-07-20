package com.mykotlinapps.bodybuilder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class WorkoutSession(
    val name: String,
    val duration: String,
    val date: Date
)

