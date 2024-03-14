package com.mita.gamebuddymobile.api


import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


interface ApiService {
    @POST("login")
    fun login(@Body login: Login): Call<LoginResponse>

    @POST("register")
    fun register(@Body user: User): Call<User>

    @GET("list")
    fun getUser(): Call<List<UserDataClass>>

    @GET("messages/{conversationId}")
    fun getMessages(@Path("conversationId") conversationId: Int): Call<List<Message>>

    @POST("messages/{conversationId}")
    fun sendMessage(@Path("conversationId") conversationId: Int, @Body request: Map<String, String>): Call<Message>

    @GET("conversations/{conversationId}")
    fun getConversation(@Path("conversationId") conversationId: Int): Call<ConversationResponse>

    @POST("conversations/{receiverId}/messages")
    fun postConversation(@Path("receiverId") conversationId: Int): Call<ConversationResponse>

    @GET("/start-matching")
    fun startMatching(): Call<StartMatchingResponse>

    @GET("user-profile")
    fun getUserProfile(): Call<UserProfileResponse>

}
