package ru.itis.easyenglish.theory

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "words", indices = [Index(value = ["englishWord"], unique = true)])
data class WordEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val englishWord: String,
    val russianWord: String,
    val level: Int?,
    var completedStatus: Boolean?,
    var savedStatus: Boolean
)