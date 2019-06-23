package com.example.chatbot.model

data class ChatBotServerResponseModel(
    val success: String?,
    val errorMessage: String?,
    val message: ChatBotServerReplyModel?

)


data class ChatBotServerReplyModel(
    val chatBotName: String?,
    val chatBotID: String?,
    val message: String?,
    val emotion: String?
)