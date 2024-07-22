package com.mykotlinapps.bodybuilder

import com.mykotlinapps.bodybuilder.data.Exercise
import com.mykotlinapps.bodybuilder.data.UserDetails
import com.mykotlinapps.bodybuilder.data.Workout
import com.mykotlinapps.bodybuilder.data.WorkoutTemplate
import java.util.Date


data class User(
    val userDetails: UserDetails = UserDetails(),
    val analytics: Analytics = Analytics(),
    val workouts: List<WorkoutTemplate> = emptyList()
) {
    constructor() : this(UserDetails(), Analytics(), emptyList())
}