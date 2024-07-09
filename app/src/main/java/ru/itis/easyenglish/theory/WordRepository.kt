package ru.itis.easyenglish.theory

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WordRepository(private val wordDao: WordDao) {

    suspend fun getWords(): List<WordEntity> {
        return withContext(Dispatchers.IO) {
            wordDao.getAllWords()
        }
    }

    suspend fun updateWord(word: WordEntity) {
        wordDao.update(word)
    }

    suspend fun getWordsOfLevel(level: Int): List<WordEntity> {
        return withContext(Dispatchers.IO) {
            wordDao.getWordsByLevel(level)
        }
    }

    suspend fun getCompletedWordsCount(level: Int): Int {
        return wordDao.getCompletedWordsCount(level)
    }

    suspend fun getTotalWordsCount(level: Int): Int {
        return wordDao.getTotalWordsCount(level)
    }
}