package com.kugemi.poemwriter.viewmodels

import com.kugemi.poemwriter.model.local_dto.SavedPoem
import java.io.Serializable

data class PoemViewModel (val poem : SavedPoem,
                          val id : Int = poem.id,
                          val text : String = poem.text,
                          val previewText : String = poem.text.substringBefore("\n"),
                          val syllables : String = poem.syllablesInfo,
                          val language : String = poem.language) : Serializable