package com.kugemi.poemwriter.infrastructure.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.kugemi.poemwriter.model.local_dto.SavedPoem

@Dao
abstract class APoemDao {
    @Query("SELECT * FROM POEMS")
    abstract fun savedPoems(): LiveData<List<SavedPoem>>

    @Query("SELECT EXISTS(SELECT * FROM POEMS WHERE id = :id)")
    abstract fun isPoemIsExist(id : Int) : Boolean

    @Delete
    abstract fun deletePoem(poem : SavedPoem)

    @Insert
    abstract fun insertPoem(poem : SavedPoem)

    @Query("UPDATE POEMS SET text = :newText WHERE id = :id")
    abstract fun updatePoemText(newText : String, id : Int)

    @Query("UPDATE POEMS SET syllablesInfo = :newSyllablesInfo WHERE id = :id")
    abstract fun updatePoemSyllables(newSyllablesInfo : String, id : Int)

    @Query("UPDATE POEMS SET language = :newLanguage WHERE id = :id")
    abstract fun updatePoemLanguage(newLanguage : String, id : Int)

}