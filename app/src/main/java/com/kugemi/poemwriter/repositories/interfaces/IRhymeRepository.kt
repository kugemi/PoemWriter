package com.kugemi.poemwriter.repositories.interfaces

import com.kugemi.poemwriter.model.local_dto.RhymeModel

interface IRhymeRepository {
    suspend fun getRhymes(word : String, language: String) : List<RhymeModel>
}