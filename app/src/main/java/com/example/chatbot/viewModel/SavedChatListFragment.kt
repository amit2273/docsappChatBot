package com.example.chatbot.viewModel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatbot.R
import com.example.chatbot.adapters.ChatAdapter
import com.example.chatbot.adapters.ChatListAdapter
import com.example.chatbot.database.entity.ChatMessageEntity
import com.example.chatbot.model.ChatModel
import com.example.chatbot.util.Tags
import kotlinx.android.synthetic.main.activity_main.*

class SavedChatListFragment : Fragment() {

    private var chatAdapter: ChatAdapter? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return view ?: inflater.inflate(R.layout.fragment_chat_list, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val entityObject = arguments?.getParcelable<ChatMessageEntity>(Tags.CHAT_ENTITY_OBJECT)
        chatAdapter = ChatAdapter()
        chat_recycler_view.adapter = chatAdapter
        chat_recycler_view.layoutManager = LinearLayoutManager(requireContext())
        chat_recycler_view.background = ContextCompat.getDrawable(requireContext(),R.drawable.background)
        val list = mutableListOf<ChatModel>()
        //(activity as MainActivity).getChatMessages()
        entityObject?.let {
            it.message?.forEach { message ->
                list.add(ChatModel(message?.chatMessage, message?.isUser ?: true))
            }
        }

        updateMessage(list)
    }

    private fun updateMessage(list: List<ChatModel>) {
        chatAdapter?.submitList(list)
        chatAdapter?.notifyDataSetChanged()
        chatAdapter?.let {
            chat_recycler_view.smoothScrollToPosition(it.itemCount - 1)
        }


    }
}