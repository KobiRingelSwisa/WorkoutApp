package com.mykotlinapps.bodybuilder.data

import java.util.Date
import java.util.UUID

data class Workout(
    val name: String = "",
    val id: String = UUID.randomUUID().toString(),
    val duration: String = "",
    val date: Date = Date(),
    val exercises: List<Exercise> = emptyList()
) {
    // No-argument constructor for Firestore
    constructor() : this("", id = UUID.randomUUID().toString(),"", Date(), emptyList())
}
