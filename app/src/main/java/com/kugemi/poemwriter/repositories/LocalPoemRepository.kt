package com.kugemi.poemwriter.repositories

import androidx.lifecycle.LiveData
import com.kugemi.poemwriter.infrastructure.database.PoemDatabase
import com.kugemi.poemwriter.model.local_dto.SavedPoem
import com.kugemi.poemwriter.repositories.interfaces.IPoemRepository

class LocalPoemRepository (private val database: PoemDatabase) : IPoemRepository {
    override fun getPoems(): LiveData<List<SavedPoem>> {
        return database.Dao().savedPoems()
    }

    override suspend fun putPoem(poem: SavedPoem) {
        database.Dao().insertPoem(poem)
    }

    override suspend fun delete(poem: SavedPoem) {
        database.Dao().deletePoem(poem)
    }

    override suspend fun updateText(newText : String, id : Int) {
        database.Dao().updatePoemText(newText, id)
    }

    override suspend fun updateSyllables(newSyllables: String, id: Int) {
        database.Dao().updatePoemSyllables(newSyllables, id)
    }

    override suspend fun isPoemIsExist(id: Int): Boolean {
        return database.Dao().isPoemIsExist(id)
    }

    override suspend fun updateLanguage(newLanguage: String, id: Int) {
        return database.Dao().updatePoemLanguage(newLanguage, id)
    }


}