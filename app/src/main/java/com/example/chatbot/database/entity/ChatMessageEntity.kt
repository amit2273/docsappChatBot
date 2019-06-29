package com.example.chatbot.database.entity

import android.os.Parcelable
import androidx.room.*
import com.example.chatbot.util.MessageTypeConverter
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "chatMessages")
@TypeConverters(MessageTypeConverter::class)
@Parcelize
data class ChatMessageEntity(
    var message: List<Messages?>? = null,

    @PrimaryKey
    var chatID: Int = 0
):Parcelable

@Parcelize
data class Messages(
    var chatMessage: String? = null,

    var isUser: Boolean = false,

    var mID: Int = 0
):Parcelable