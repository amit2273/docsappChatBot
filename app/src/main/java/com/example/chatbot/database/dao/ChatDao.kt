package com.example.chatbot.database.dao

import androidx.room.*
import com.example.chatbot.database.entity.ChatMessageEntity
import io.reactivex.Flowable

@Dao
interface ChatDao {
    @Query("SELECT * FROM chatMessages ORDER BY chatID")
    fun getChatMessage(): Flowable<List<ChatMessageEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveChatMessages(chatMessage: ChatMessageEntity)

}