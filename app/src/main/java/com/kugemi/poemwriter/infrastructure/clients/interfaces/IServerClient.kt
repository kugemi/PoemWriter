package com.kugemi.poemwriter.infrastructure.clients.interfaces

import com.kugemi.poemwriter.model.local_dto.RhymeModel

interface IServerClient {
    suspend fun getRhymes(word : String, language : String) : List<RhymeModel>
}