package com.example.androidproject.dto

import android.net.Uri

data class UserRegistrationForm(
    var fullName: String = "",
    var email: String = "",
    var password: String = "",
    var yearsOfExperience : Int = 0,
    var imageUri: Uri? = null
)