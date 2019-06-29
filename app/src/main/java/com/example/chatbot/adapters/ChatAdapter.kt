package com.example.chatbot.adapters

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
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

    override fun getItemCount(): Int {
        return super.getItemCount()
    }


    inner class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val chatUser: TextView = itemView.findViewById(R.id.user_chat_message)
        val chatBot: TextView = itemView.findViewById(R.id.bot_chat_message)
        val chatUserCard: CardView = itemView.findViewById(R.id.userChat)
        val chatBotCard: CardView = itemView.findViewById(R.id.botChat)
        fun bindItem(item: ChatModel) {
            if(item.isUserChat){
                chatUser.text = item.chatMessage
                chatBotCard.visibility = View.GONE
                chatUserCard.visibility = View.VISIBLE
            }else{
                chatBot.text = item.chatMessage
                chatUserCard.visibility = View.GONE
                chatBotCard.visibility = View.VISIBLE
            }

        }
    }



}