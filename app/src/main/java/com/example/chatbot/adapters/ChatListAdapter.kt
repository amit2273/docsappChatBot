package com.example.chatbot.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.chatbot.R
import com.example.chatbot.database.entity.ChatMessageEntity


class ChatListAdapter(private val onClickListener : onCLickListener ) : androidx.recyclerview.widget.ListAdapter<ChatMessageEntity, ChatListAdapter.ChatViewHolder>(PolDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        return ChatViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.cell_chat_list, parent, false))

    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.bindItem(getItem(position))
       holder.llCHatLay.setOnClickListener {
           onClickListener.onChatWindowClicked(getItem(position))
       }

    }

    interface onCLickListener{
        fun onChatWindowClicked(item : ChatMessageEntity)
    }

    class PolDiff : DiffUtil.ItemCallback<ChatMessageEntity>() {
        override fun areItemsTheSame(oldItem: ChatMessageEntity, newItem: ChatMessageEntity): Boolean {
            return oldItem.chatID == newItem.chatID
        }

        override fun areContentsTheSame(oldItem: ChatMessageEntity, newItem: ChatMessageEntity): Boolean {
            return oldItem == newItem
        }
    }


    inner class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val chatListMessage: TextView = itemView.findViewById(R.id.chat_list)
        val llCHatLay: LinearLayout = itemView.findViewById(R.id.llchatLay)
        fun bindItem(item: ChatMessageEntity) {
            chatListMessage.text = "Chat Window" + item.chatID
        }
    }


}