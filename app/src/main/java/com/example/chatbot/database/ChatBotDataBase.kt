package com.example.chatbot.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.chatbot.database.dao.ChatDao
import com.example.chatbot.database.entity.ChatMessageEntity

@Database(entities = [ChatMessageEntity::class], version = 1)
abstract class ChatBotDataBase: RoomDatabase() {

    companion object {
        @Volatile
        private var instance: ChatBotDataBase? = null
        private val DB_NAME = "ChatMessages.db"

        fun getInstance(context: Context): ChatBotDataBase {
            if (instance == null) {
                instance = create(context)
            }
            return instance!!
        }

        private fun create(context: Context): ChatBotDataBase {
            return Room.databaseBuilder(
                    context,
                    ChatBotDataBase::class.java,
                    DB_NAME).build()
        }

    }

    abstract fun getChatMessages(): ChatDao

}