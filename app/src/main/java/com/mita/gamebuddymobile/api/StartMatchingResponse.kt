package com.mita.gamebuddymobile.api


import com.google.gson.annotations.SerializedName

data class StartMatchingResponse(
    @SerializedName("userId")
    val userId: Int,
    @SerializedName("interestsId")
    val interestsId: Int
)
