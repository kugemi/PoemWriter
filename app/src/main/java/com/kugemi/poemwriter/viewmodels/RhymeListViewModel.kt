package com.kugemi.poemwriter.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.kugemi.poemwriter.repositories.interfaces.IRhymeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

open class RhymeListViewModel @ViewModelInject constructor(private val repository: IRhymeRepository) : ViewModel() {
    private val myRhymeList = MutableLiveData<List<RhymeViewModel>>()

    val rhymeList : LiveData<List<RhymeViewModel>>
        get() = myRhymeList

    var word = ""
    var language = ""

    init {
        viewModelScope.launch(Dispatchers.IO) {
            myRhymeList.postValue(repository.getRhymes(word, language).map { rhyme ->
                RhymeViewModel(rhyme)
            })
        }
    }
}