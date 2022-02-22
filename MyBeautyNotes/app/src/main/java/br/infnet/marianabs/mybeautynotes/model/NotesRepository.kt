package br.infnet.marianabs.mybeautynotes.model

import androidx.lifecycle.LiveData

class NotesRepository(private val noteDao: NotesDao) {

    val allNotes: LiveData<List<Notes>> = noteDao.getAllNotes()

    suspend fun insertNote(note: Notes) {
        noteDao.insertNote(note)
    }
    suspend fun updateNote(note: Notes) {
        noteDao.updateNote(note)
    }
    suspend fun deleteNote(note: Notes) {
        noteDao.deleteNote(note)
    }
    suspend fun deleteAllNote() {
        noteDao.deleteAllNote()
    }
}