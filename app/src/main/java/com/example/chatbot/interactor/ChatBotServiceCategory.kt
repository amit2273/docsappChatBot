package `in`.medibuddy.domain.interactor.infiniti

import com.example.chatbot.model.ChatBotServerRequestModel
import com.example.chatbot.model.ChatBotServerResponseModel
import com.example.chatbot.remote.RemoteServiceFactory
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.DisposableSubscriber

open class ChatBotServiceCategory  {
    private val disposables = CompositeDisposable()
    private val service = RemoteServiceFactory.makeChatBotService(true)
    open fun executeChatBotServiceRequest(singleObserver: DisposableSubscriber<ChatBotServerResponseModel>, chatBotServerRequestModel: ChatBotServerRequestModel): DisposableSubscriber<ChatBotServerResponseModel>? {
        val single = service.getMessage(chatBotServerRequestModel.apiKey,chatBotServerRequestModel.chatBotId,chatBotServerRequestModel.message,chatBotServerRequestModel.externalId,chatBotServerRequestModel.firstName,chatBotServerRequestModel.lastName,chatBotServerRequestModel.gender)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        var disposableSubscriber: DisposableSubscriber<ChatBotServerResponseModel>
        disposableSubscriber = single.subscribeWith(singleObserver)
        disposables.add(disposableSubscriber)
        return disposableSubscriber
    }

    fun dispose() {
        if (!disposables.isDisposed) disposables.clear()
    }


}