package com.mykotlinapps.bodybuilder.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Exercise(
    var bodyPart: String = "",
    var equipment: String = "",
    var gifUrl: String = "",
    var id: String = "",
    var name: String = "",
    var target: String = "",
    var secondaryMuscles: List<String> = listOf(),
    var instructions: List<String> = listOf()
): Parcelable {
    // No-argument constructor required by Firestore
    constructor() : this("", "", "", "", "", "", listOf(), listOf())
}
