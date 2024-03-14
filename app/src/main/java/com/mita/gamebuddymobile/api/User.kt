package com.mita.gamebuddymobile.api

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("password")
    val password: String,

    @SerializedName("age")
    val age: Int,// New field for age

    @SerializedName("gender")
    val gender: Int
)






