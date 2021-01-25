package com.kugemi.poemwriter.infrastructure.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kugemi.poemwriter.model.local_dto.SavedPoem

@Database(entities = [SavedPoem::class], version = 3) //exportSchema = false
abstract class PoemDatabase : RoomDatabase() {
    abstract fun Dao(): APoemDao
}