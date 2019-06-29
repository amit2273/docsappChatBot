package com.example.chatbot.viewModel

import `in`.medibuddy.domain.interactor.infiniti.ChatBotServiceCategory
import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chatbot.Resource
import com.example.chatbot.ResourceState
import com.example.chatbot.model.ChatBotServerRequestModel
import com.example.chatbot.model.ChatBotServerResponseModel
import io.reactivex.subscribers.DisposableSubscriber


class ChatViewModel   : ViewModel() {

    private val chatBotRequestLiveData = MutableLiveData<Resource<ChatBotServerResponseModel>>()
    private var chatBotServiceCategory = ChatBotServiceCategory()


    fun getChatBotLiveData() = chatBotRequestLiveData

    fun getChatBotServiceRequest(chatBotServerRequestModel: ChatBotServerRequestModel) {
        chatBotRequestLiveData.value = Resource(ResourceState.LOADING, null, null)
        chatBotServiceCategory.executeChatBotServiceRequest(ChatBotDisposableSubscriber(), chatBotServerRequestModel)
    }


    private inner class ChatBotDisposableSubscriber : DisposableSubscriber<ChatBotServerResponseModel>() {
        override fun onNext(t: ChatBotServerResponseModel?) {
            chatBotRequestLiveData.value = Resource(ResourceState.SUCCESS, t, null)
        }

        override fun onError(t: Throwable?) {
            chatBotRequestLiveData.value = Resource(ResourceState.ERROR, null, null)
        }

        override fun onComplete() {
        }


    }



    override fun onCleared() {
        super.onCleared()
        chatBotServiceCategory.dispose()
    }

}