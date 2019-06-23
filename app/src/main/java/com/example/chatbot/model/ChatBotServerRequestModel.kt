package com.example.chatbot.model

data class ChatBotServerRequestModel(
    val apiKey : String ="6nt5d1nJHkqbkphe",
    val message: String?,
    val chatBotId: String = "63906",
    val externalId: String = "Amit Randhawa",
    val firstName: String = "Amit",
    val lastName: String = "Randhawa",
    val gender: String = "m"
)