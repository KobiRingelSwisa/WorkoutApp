package com.mykotlinapps.bodybuilder.data

data class UserDetails(
    val fullName: String = "",
    val email: String = "",
    val heightInCm: Int = 0,
    val weightInKg: Int = 0,
    val gender: String = "",
    val age: Int = 0
)
