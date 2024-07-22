package com.mykotlinapps.bodybuilder.data

import java.util.Date

data class Workout(
    val name: String = "",
    val duration: String = "",
    val date: Date = Date(),
    val exercises: List<Exercise> = emptyList()
) {
    // No-argument constructor for Firestore
    constructor() : this("", "", Date(), emptyList())
}
