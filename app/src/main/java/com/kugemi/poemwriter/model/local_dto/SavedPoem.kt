package com.kugemi.poemwriter.model.local_dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "POEMS")
class SavedPoem {
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0
    var text : String = ""
    var syllablesInfo : String = ""
    var language : String = ""
}