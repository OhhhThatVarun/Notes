package com.varun.notes.domain.models

data class Note(
        val id: String,
        val title: String?,
        val description: String?,
        val imageUrl: String?,
        val createdAt: Long,
        val isEdited: Boolean,
)
