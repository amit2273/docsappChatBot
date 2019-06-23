package com.example.chatbot.adapters

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.chatbot.R
import com.example.chatbot.model.ChatModel


class ChatAdapter() : androidx.recyclerview.widget.ListAdapter<ChatModel, ChatAdapter.ChatViewHolder>(PolDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        return ChatViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.cell_chat, parent, false))

    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.bindItem(getItem(position))

    }

    class PolDiff : DiffUtil.ItemCallback<ChatModel>() {
        override fun areItemsTheSame(oldItem: ChatModel, newItem: ChatModel): Boolean {
            return oldItem.chatMessage.equals(newItem.chatMessage)
        }

        override fun areContentsTheSame(oldItem: ChatModel, newItem: ChatModel): Boolean {
            return oldItem == newItem
        }
    }


    inner class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val chatMessage: TextView = itemView.findViewById(R.id.chat_message)
        fun bindItem(item: ChatModel) {
            chatMessage.text = item.chatMessage
            if(item.isUserChat){
                chatMessage.gravity = Gravity.RIGHT
            }else{
                chatMessage.gravity = Gravity.LEFT
            }

        }
    }



}