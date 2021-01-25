package com.kugemi.poemwriter.infrastructure.clients.interfaces

import com.kugemi.poemwriter.model.server_dto.ServerRhyme
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface IApiDefinition {
    @GET("talk")
    fun getRhymes(@Query("function") function : String, @Query("word") word : String, @Query("lang") language : String) : Deferred<List<ServerRhyme>>
}