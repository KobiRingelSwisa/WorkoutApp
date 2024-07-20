package com.mykotlinapps.bodybuilder

data class UserDetails(
    val fullName: String = "",
    val email: String = "",
    val heightInCm: Int = 0,
    val weightInKg: Int = 0,
    val gender: String = "",
    val age: Int = 0
)

data class Analytics(
    val someMetric: Int = 0

    // Add other fields as necessary
)

data class User(
    val userDetails: UserDetails = UserDetails(),
    val analytics: Analytics = Analytics()
)
