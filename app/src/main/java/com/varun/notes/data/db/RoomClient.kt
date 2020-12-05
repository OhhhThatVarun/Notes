package com.varun.notes.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.varun.notes.data.db.dao.NotesDao
import com.varun.notes.data.entities.NoteEntity

@Database(entities = [NoteEntity::class], version = 1, exportSchema = false)
abstract class RoomClient : RoomDatabase() {

    abstract fun notesDao(): NotesDao

    companion object {
        private var INSTANCE: RoomClient? = null
        private const val DB_NAME = "notes.db"

        fun getDatabase(context: Context): RoomClient {
            if (INSTANCE == null) {
                synchronized(RoomClient::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(context.applicationContext, RoomClient::class.java, DB_NAME)
                                .allowMainThreadQueries()
                                .build()
                    }
                }
            }
            return INSTANCE!!
        }
    }
}