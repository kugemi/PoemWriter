package com.kugemi.poemwriter.viewmodels

import com.kugemi.poemwriter.model.local_dto.RhymeModel

data class RhymeViewModel (val rhyme : RhymeModel, val word: String = rhyme.word,
                           val syllables: Int = rhyme.syllables,
                           val frequency: Int = rhyme.frequency)