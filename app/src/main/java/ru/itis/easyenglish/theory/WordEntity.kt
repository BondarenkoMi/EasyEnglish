package ru.itis.easyenglish.theory

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "words")
data class WordEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val englishWord: String,
    val russianWord: String,
    val level: Int?,
    val completedStatus: Boolean?,
    val savedStatus: Boolean?
)