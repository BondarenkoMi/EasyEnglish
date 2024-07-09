package ru.itis.easyenglish.theory

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface WordDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(word: WordEntity)

    @Query("SELECT * FROM words")
    suspend fun getAllWords(): List<WordEntity>

    @Update
    suspend fun update(word: WordEntity)

    @Query("SELECT COUNT(*) FROM words WHERE level = :level AND completedStatus = 1")
    suspend fun getCompletedWordsCount(level: Int): Int

    @Query("SELECT * FROM words WHERE savedStatus = 1")
    suspend fun getSavedWords(): List<WordEntity>

    @Query("SELECT * FROM words WHERE level = :level")
    suspend fun getWordsByLevel(level: Int): List<WordEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(words: List<WordEntity>)

    @Query("SELECT COUNT(*) FROM words WHERE level = :level")
    suspend fun getTotalWordsCount(level: Int): Int
}