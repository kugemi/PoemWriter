package com.kugemi.poemwriter.repositories.interfaces

import androidx.lifecycle.LiveData
import com.kugemi.poemwriter.model.local_dto.SavedPoem

interface IPoemRepository {
    fun getPoems(): LiveData<List<SavedPoem>>
    suspend fun putPoem(poem : SavedPoem)
    suspend fun delete(poem: SavedPoem)
    suspend fun updateText(newText : String, id : Int)
    suspend fun updateSyllables(newSyllables : String, id : Int)
    suspend fun updateLanguage(newLanguage : String, id : Int)
    suspend fun isPoemIsExist(id: Int) : Boolean

}