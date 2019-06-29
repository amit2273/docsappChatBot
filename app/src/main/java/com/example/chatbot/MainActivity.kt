package com.example.chatbot

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatbot.adapters.ChatAdapter
import com.example.chatbot.adapters.ChatListAdapter
import com.example.chatbot.database.entity.ChatMessageEntity
import com.example.chatbot.database.entity.Messages
import com.example.chatbot.model.ChatBotServerRequestModel
import com.example.chatbot.model.ChatBotServerResponseModel
import com.example.chatbot.model.ChatModel
import com.example.chatbot.util.Tags
import com.example.chatbot.viewModel.ChatViewModel
import com.example.chatbot.viewModel.SavedChatListFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), ChatListAdapter.onCLickListener {
    override fun onChatWindowClicked(item: ChatMessageEntity) {
        val fragment = SavedChatListFragment()
        val bundle = Bundle()
        bundle.putParcelable(Tags.CHAT_ENTITY_OBJECT,item)
        fragment.arguments = bundle
        addFragment(flHomeFragmentHolder.id, supportFragmentManager, fragment, tag = "chatListFragment")
        true

    }

    private var chatAdapter: ChatAdapter? = null
    private val listOfChatMessages = mutableListOf<ChatModel>()
    private lateinit var chatViewModel: ChatViewModel
    private lateinit var cacheImpl: ChatMessageCacheImpl
    private var messageID: Int = 0
    private var chatID: Int = 0// I have used ID for quick demo. We can use time stamp instead
    private var listOfMessages = mutableListOf<Messages>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        chatAdapter = ChatAdapter()
        cacheImpl = ChatMessageCacheImpl(this)
        chat_recycler_view.adapter = chatAdapter
        chat_recycler_view.layoutManager = LinearLayoutManager(this)
        chatViewModel = ViewModelProviders.of(this).get(ChatViewModel::class.java)
        chatViewModel.getChatBotLiveData().observe(this, Observer { responseFromServer(it) })
        onSendClicked()
        chatID = getChatID()
        saveChatID(chatID +1)
    }

    private fun onSendClicked() {
        bt_send.setOnClickListener {
            if (!typed_message.text.toString().isNullOrEmpty()) {
                listOfMessages.add(Messages(typed_message.text.toString(), true, messageID++))
                saveChatMessages(ChatMessageEntity(listOfMessages, chatID))
                chatViewModel.getChatBotServiceRequest(ChatBotServerRequestModel(message = typed_message.text.toString()))
                listOfChatMessages.add(ChatModel(typed_message.text.toString()))
                typed_message.text?.clear()
                updateMessage(listOfChatMessages)

            }

        }
    }

    private fun saveChatID(chatID: Int) {
        val sharedPref = getPreferences(Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()) {
            putInt(Tags.CHAT_ID, chatID)
            apply()
        }
    }

    private fun getChatID(): Int {
        val sharedPref = getPreferences(Context.MODE_PRIVATE) ?: return 0
        return sharedPref.getInt(Tags.CHAT_ID, 0)
    }


    private fun saveChatMessages(model: ChatMessageEntity) {
        cacheImpl.saveChatMessages(model).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe {
                Log.d("Ids Saved", "Message Saved Successfully")
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
                        listOfMessages.add(Messages(serverMessage.message, false, messageID++))
                        saveChatMessages(ChatMessageEntity(listOfMessages, chatID))
                        listOfChatMessages.add(ChatModel(serverMessage.message, false))
                        updateMessage(listOfChatMessages)
                       // getChatMessages()

                    }
                }
            }
            ResourceState.ERROR -> {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
                Log.e("Error", "Something Went Wrong")

            }
        }
    }




    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.list -> {
                val fragment = ChatListFragment()
                addFragment(flHomeFragmentHolder.id, supportFragmentManager, fragment, tag = "chatListFragment")
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home, menu)
        return super.onCreateOptionsMenu(menu)
    }


    private fun updateMessage(list: List<ChatModel>) {
        chatAdapter?.submitList(list)
        chatAdapter?.notifyDataSetChanged()
        chatAdapter?.let {
            chat_recycler_view.smoothScrollToPosition(it.itemCount - 1)
        }


    }


    fun addFragment(
        holderID: Int,
        fragmentManager: FragmentManager,
        fragment: Fragment,
        tag: String? = null) {
        fragmentManager.beginTransaction()
            .add(holderID, fragment, tag)
            .addToBackStack(null)
            .commitAllowingStateLoss()
    }

}
