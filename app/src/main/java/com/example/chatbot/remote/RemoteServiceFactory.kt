package com.example.chatbot.remote


import com.squareup.moshi.Moshi
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import okhttp3.OkHttpClient
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.lang.Exception

object RemoteServiceFactory {

    private const val BASE_URL = "http://www.personalityforge.com"
    private var sevices: ChatBotService? = null
    fun makeChatBotService(isDebug: Boolean): ChatBotService {
        return if (sevices == null) {
            val okHttpClient = makeOkHttpClient(getErrorHandlerInterceptor(), isDebug)
            makeChatBotService(okHttpClient)
        } else
            sevices!!
    }


    private fun makeOkHttpClient(interceptor: Interceptor, isDebug: Boolean): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }

    private fun makeChatBotService(okHttpClient: OkHttpClient): ChatBotService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(Moshi.Builder().build()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        sevices = retrofit.create(ChatBotService::class.java)
        return sevices!!
    }





    private fun getErrorHandlerInterceptor(): Interceptor {
        return Interceptor { chain ->
            val request = chain.request()
            val response = chain.proceed(request)

            when (response.code()) {
                204 -> throw Exception()
              /*  204, 205 -> throw NoContentFoundException(response.code(), "No content found")
//                401,403 -> throw AuthorizationFailureException(response.code(), "Invalid token.You are not an authorized user")
                500, 502, 503 -> throw InternalServerException(response.code(), "Oops..Server is not responding in this moment. Please try again.")
                404 -> throw ResourceNotFoundException(response.code(), "Invalid Request. Check with url or params used while making call")
                406 -> throw InvalidUploadFormatException(response.code(), "Invalid upload format.")
         */   }
            response
        }
    }
}