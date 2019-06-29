package com.example.chatbot.util

import androidx.room.TypeConverter
import com.example.chatbot.database.entity.Messages
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MessageTypeConverter: BaseConverter<Messages>() {
    @TypeConverter
    override fun fromListForEntity(list: List<Messages?>?) = Gson().toJson(list)

    @TypeConverter
    override fun toListForEntity(string: String): List<Messages?>? {
        if(string.isNullOrEmpty()) mutableListOf<Messages>()
        val listType = object : TypeToken<List<Messages?>>() {}.type
        return  Gson().fromJson(string, listType)
    }

}



abstract class BaseConverter<T>{

    abstract fun fromListForEntity(list: List<T?>?): String

    abstract fun toListForEntity(string: String): List<T?>?
}