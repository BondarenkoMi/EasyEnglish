package ru.itis.easyenglish.practice

import ru.itis.easyenglish.theory.WordEntity
import ru.itis.easyenglish.theory.WordRepository

class Level(
    val dif: Int,
    var countWords: Int,
    private val wordRepository: WordRepository,
    val words: MutableList<WordEntity> = mutableListOf(),
    val learnedWords: MutableList<WordEntity> = mutableListOf()
) {
    suspend fun loadWords() {
        words.clear()
        words.addAll(wordRepository.getWordsOfLevel(dif))
    }
}