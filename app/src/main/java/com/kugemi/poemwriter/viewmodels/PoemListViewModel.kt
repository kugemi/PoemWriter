package com.kugemi.poemwriter.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.kugemi.poemwriter.model.local_dto.SavedPoem
import com.kugemi.poemwriter.repositories.interfaces.IPoemRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

open class PoemListViewModel @ViewModelInject constructor(private val repository: IPoemRepository) : ViewModel() {
    val poem : MutableLiveData<PoemViewModel> = MutableLiveData()
    val poemList : LiveData<List<PoemViewModel>>
        get() = Transformations.map(repository.getPoems()) { list ->
            return@map list.map{ poem->
                PoemViewModel(poem)
            }
        }

    fun onSavePoem(poem: SavedPoem){
        viewModelScope.launch(Dispatchers.IO) {
            repository.putPoem(poem)
        }
    }

    fun onDeletePoem(poem : PoemViewModel){
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(poem.poem)
        }
    }

    fun onUpdatePoemText(newText : String, id : Int){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateText(newText, id)
        }
    }

    fun onUpdatePoemSyllables(newSyllables : String, id : Int){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateSyllables(newSyllables, id)
        }
    }

    fun onUpdatePoemLanguage(newLanguage : String, id : Int){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateLanguage(newLanguage, id)
        }
    }

}