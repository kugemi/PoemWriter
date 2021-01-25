package com.kugemi.poemwriter.infrastructure.clients

import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.kugemi.poemwriter.infrastructure.clients.interfaces.IApiDefinition
import com.kugemi.poemwriter.infrastructure.clients.interfaces.IServerClient
import com.kugemi.poemwriter.model.local_dto.RhymeModel
import com.kugemi.poemwriter.model.server_dto.ServerRhyme
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ServerClient : IServerClient {
    companion object{
        private const val BASE_URL = "https://rhymebrain.com/"
        private const val FUNCTION = "getRhymes"
    }

    val service : IApiDefinition

    init {
        val gson = GsonBuilder().create()

        val retrofit = Retrofit.Builder()
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(BASE_URL)
            .build()
        service = retrofit.create(IApiDefinition::class.java)
    }

    override suspend fun getRhymes(word: String, language : String): List<RhymeModel> {
        return try{
            val serverResult : List<ServerRhyme> = service.getRhymes(FUNCTION, word, language).await()
            serverResult.map { serverRhyme ->
                RhymeModel(serverRhyme.word, serverRhyme.syllables, serverRhyme.freq)
            }
        } catch (exception : HttpException) {
            listOf(RhymeModel("HttpException", 0, 0))
        }
    }
}