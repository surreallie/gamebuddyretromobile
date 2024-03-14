package com.mita.gamebuddymobile.api

data class Message(
    val id: Int,
    val senderId: Int,
    val receiverId: Int,
    val body: String,
)
