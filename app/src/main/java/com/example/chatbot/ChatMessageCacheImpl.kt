package com.example.chatbot

import android.content.Context
import com.example.chatbot.database.ChatBotDataBase
import com.example.chatbot.database.entity.ChatMessageEntity
import com.example.chatbot.interactor.ChatMessageCache
import io.reactivex.Completable
import io.reactivex.Flowable


class ChatMessageCacheImpl(private val context: Context) : ChatMessageCache {

    val ChatbotDataBase = ChatBotDataBase.getInstance(context)

    override fun getChatMessages(): Flowable<List<ChatMessageEntity>>  {
        return ChatbotDataBase.getChatMessages().getChatMessage()
    }

    override fun saveChatMessages(model: ChatMessageEntity): Completable {
        return Completable.fromAction { ChatbotDataBase.getChatMessages().saveChatMessages(model) }
    }
}