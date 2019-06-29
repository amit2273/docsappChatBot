package com.example.chatbot.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class ChatModel(
        val chatMessage: String? = null,
        val isUserChat : Boolean = true
)