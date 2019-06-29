package com.example.chatbot.interactor

import com.example.chatbot.database.entity.ChatMessageEntity
import io.reactivex.Completable
import io.reactivex.Flowable


interface ChatMessageCache {

    fun saveChatMessages(model: ChatMessageEntity): Completable

    fun getChatMessages(): Flowable<List<ChatMessageEntity>>

}