package com.varun.notes.data.db.dao

import androidx.room.*
import com.varun.notes.data.entities.NoteEntity

@Dao
interface NotesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(note: NoteEntity)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun updateNote(note: NoteEntity)

    @Query("DELETE FROM NoteEntity WHERE id = :id")
    suspend fun deleteNote(id: String)

    @Query("SELECT * FROM NoteEntity ORDER BY CREATED_AT ASC")
    suspend fun getNotes(): List<NoteEntity>

    @Query("SELECT * FROM NoteEntity WHERE id = :id LIMIT 1")
    suspend fun getNoteById(id: String): NoteEntity
}