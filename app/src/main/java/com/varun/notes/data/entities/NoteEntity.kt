package com.varun.notes.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.varun.notes.domain.models.Note

@Entity
data class NoteEntity(
        @PrimaryKey(autoGenerate = false)
        val id: String,
        @ColumnInfo(name = "TITLE")
        val title: String?,
        @ColumnInfo(name = "DESCRIPTION")
        val description: String?,
        @ColumnInfo(name = "IMAGE_URL")
        val imageUrl: String?,
        @ColumnInfo(name = "CREATED_AT")
        val createdAt: Long?,
        @ColumnInfo(name = "IS_EDITED")
        val isEdited: Boolean = false,
) {
    fun transform(): Note {
        return Note(id, title, description, imageUrl, createdAt, isEdited)
    }
}

fun Note.transform(): NoteEntity {
    return NoteEntity(id, title, description, imageUrl, createdAt, isEdited)
}
