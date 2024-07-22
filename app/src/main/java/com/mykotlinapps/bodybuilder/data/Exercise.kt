package com.mykotlinapps.bodybuilder.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Exercise(
    val id: String,
    var name: String,
    val bodyPart: String,
    val target: String,
    val equipment: String,
    val gifUrl: String,
    val secondaryMuscles: List<String> = listOf(),
    val instructions: List<String> = listOf()
): Parcelable {
    // No-argument constructor required by Firestore
    constructor() : this("", "", "", "", "", "", listOf(), listOf())
}
