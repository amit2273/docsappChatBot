package com.example.chatbot

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatbot.adapters.ChatListAdapter
import com.example.chatbot.database.entity.ChatMessageEntity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class ChatListFragment : Fragment(){

    private lateinit var cacheImpl : ChatMessageCacheImpl
    private var chatListAdapter: ChatListAdapter? = null
    private lateinit var onClicListener : ChatListAdapter.onCLickListener


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return view ?: inflater.inflate(R.layout.fragment_chat_list, container, false)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        onClicListener = context as MainActivity
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        chatListAdapter = ChatListAdapter(onClicListener)
        chat_recycler_view.adapter = chatListAdapter
        chat_recycler_view.layoutManager = LinearLayoutManager(requireContext())
        getChatMessages()
        //(activity as MainActivity).getChatMessages()
    }


    private fun getChatMessages() {
        cacheImpl = ChatMessageCacheImpl(requireContext())
        val subscriber = cacheImpl.getChatMessages().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe {
                updateAdapter(it)
            }
    }

    private fun updateAdapter(list : List<ChatMessageEntity>){
        chatListAdapter?.submitList(list)
        chatListAdapter?.notifyDataSetChanged()
        chatListAdapter?.let {
            if(it.itemCount >1){
                chat_recycler_view.smoothScrollToPosition(it.itemCount - 1)
            }

        }

    }


    override fun onDestroy() {
        //unsubscribe subscriber
        super.onDestroy()
    }



}