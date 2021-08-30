package com.example.myapplication.utils

import java.util.*

/**
 * Model class containing personal information that will be saved to SharedPreferences.
 */
class SharedPreferenceEntry(// Name of the user.
    val name: String, dateOfBirth: Calendar, email: String
) {

    // Date of Birth of the user.
    private val mDateOfBirth: Calendar

    // Email address of the user.
    val email: String
    val dateOfBirth: Calendar
        get() = mDateOfBirth

    init {
        mDateOfBirth = dateOfBirth
        this.email = email
    }
}