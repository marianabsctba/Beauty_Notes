package br.infnet.marianabs.mybeautynotes.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import br.infnet.marianabs.mybeautynotes.model.Notes
import br.infnet.marianabs.mybeautynotes.model.NotesRepository
import br.infnet.marianabs.mybeautynotes.model.NotesRoomDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotesViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: NotesRepository
    val allNotes: LiveData<List<Notes>>

    init {
        val notesDao = NotesRoomDatabase.getDatabase(application,viewModelScope).notesDao()
        repository = NotesRepository(notesDao)
        allNotes = repository.allNotes
    }


    fun insertNote(note: Notes) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertNote(note)
    }
    fun deleteNote(note: Notes) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteNote(note)
    }
    fun deleteAllNote() = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteAllNote()
    }
}