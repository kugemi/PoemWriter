package com.kugemi.poemwriter.repositories

import com.kugemi.poemwriter.infrastructure.clients.interfaces.IServerClient
import com.kugemi.poemwriter.model.local_dto.RhymeModel
import com.kugemi.poemwriter.repositories.interfaces.IRhymeRepository

class RemoteRhymeRepository(private val serverClient: IServerClient) : IRhymeRepository {
    override suspend fun getRhymes(word: String, language : String): List<RhymeModel> {
        return serverClient.getRhymes(word, language)
    }
}