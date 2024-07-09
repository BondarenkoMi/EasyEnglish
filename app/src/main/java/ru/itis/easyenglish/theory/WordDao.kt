package ru.itis.easyenglish.theory

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WordDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(word: WordEntity)

    @Query("SELECT * FROM words")
    suspend fun getAllWords(): List<WordEntity>

    @Query("SELECT * FROM words WHERE savedStatus = 1")
    suspend fun getSavedWords(): List<WordEntity>

    @Query("SELECT * FROM words WHERE level = :level")
    suspend fun getWordsByLevel(level: Int): List<WordEntity>

    @Insert
    suspend fun insertAll(words: List<WordEntity>)
}
