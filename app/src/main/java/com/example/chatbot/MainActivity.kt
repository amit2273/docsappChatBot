package com.example.chatbot

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatbot.adapters.ChatAdapter
import com.example.chatbot.model.ChatBotServerRequestModel
import com.example.chatbot.model.ChatBotServerResponseModel
import com.example.chatbot.model.ChatModel
import com.example.chatbot.viewModel.ChatViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var chatAdapter: ChatAdapter? = null
    private val listOfChatMessages = mutableListOf<ChatModel>()
    private lateinit var chatViewModel: ChatViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        chatAdapter = ChatAdapter()
        chat_recycler_view.adapter = chatAdapter
        chat_recycler_view.layoutManager = LinearLayoutManager(this)
        chatViewModel = ViewModelProviders.of(this).get(ChatViewModel::class.java)
        chatViewModel.getChatBotLiveData().observe(this, Observer { responseFromServer(it) })
        onSendClicked()
    }

    private fun onSendClicked() {
        bt_send.setOnClickListener {
            if (!typed_message.text.toString().isNullOrEmpty()) {
                chatViewModel.getChatBotServiceRequest(ChatBotServerRequestModel(message = typed_message.text.toString()))
                listOfChatMessages.add(ChatModel(typed_message.text.toString()))
                updateMessage(listOfChatMessages)

            }

        }
    }

    private fun responseFromServer(resource: Resource<ChatBotServerResponseModel>) {
        when (resource.status) {
            ResourceState.LOADING -> {
                // show error message
            }
            ResourceState.SUCCESS -> {
                resource.data?.let {
                    it.message?.let { serverMessage ->
                        listOfChatMessages.add(ChatModel(serverMessage.message, false))
                        updateMessage(listOfChatMessages)
                    }
                }
            }
            ResourceState.ERROR -> {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
                Log.e("Error", "Something Went Wrong")

            }
        }
    }


    private fun updateMessage(list: List<ChatModel>) {
        chatAdapter?.submitList(list)
        chatAdapter?.notifyDataSetChanged()
        // chat_recycler_view.smoothScrollToPosition(chatAdapter?.itemCount - 1)

    }
}
