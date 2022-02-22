package br.infnet.marianabs.mybeautynotes.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Notes::class], version = 1, exportSchema = false)
abstract class NotesRoomDatabase : RoomDatabase() {

    abstract fun notesDao(): NotesDao

    companion object {
        @Volatile
        private var INSTANCE: NotesRoomDatabase? = null

        fun getDatabase(context: Context,scope: CoroutineScope): NotesRoomDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NotesRoomDatabase::class.java,
                    "note_database"
                ).addCallback(NoteDatabaseCallback(scope))
                .build()
                INSTANCE = instance
                return instance
            }
        }
    }
    private class NoteDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.notesDao())
                }
            }
        }

        suspend fun populateDatabase(notesDao: NotesDao) {
            notesDao.insertNote(Notes(title = "Note Tip #4",description = "Aproveite o seu app de notas!"))
            notesDao.insertNote(
                Notes(title = "Note Tip #3",description = "Quer deletar uma nota?\nApenas deslize para a direita!\n" +
                    "\n" +
                    "Arrependeu? Recupere notas em 5 segundos!\n\nSwipe>>>>>>>>")
            )
            notesDao.insertNote(Notes(title = "Note Tip #2",description = "Clique para escolher o tema diurno ou noturno"))
            notesDao.insertNote(
                Notes(title = "Note Tip #1",description = "Não esqueça de nada! Anote já!\n" +
                    "\n" +
                    "Para iniciar uma nota, clique no botão +!.")
            )

        }
    }

}