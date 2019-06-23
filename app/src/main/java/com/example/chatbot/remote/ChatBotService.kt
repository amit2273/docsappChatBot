package com.example.chatbot.remote

import com.example.chatbot.model.ChatBotServerResponseModel
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ChatBotService {

    @GET("/api/chat/")
    fun getMessage(@Query(value = "apikey", encoded = true) apikey: String?, @Query(value = "chatBotID", encoded = true) chatBotID: String?, @Query(value = "message", encoded = false) message: String?, @Query(value = "externalID", encoded = true) externalID: String?, @Query(value = "firstName", encoded = true) firstName: String?, @Query(value = "lastName", encoded = true) lastName: String?, @Query(value = "gender", encoded = true) gender: String?): Flowable<ChatBotServerResponseModel>

}