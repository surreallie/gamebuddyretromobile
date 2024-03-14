package com.mita.gamebuddymobile.api

import com.google.gson.annotations.SerializedName

data class UserProfileResponse(
    @SerializedName("interestId")
    val interestId: String
    // Add other fields as needed
)
