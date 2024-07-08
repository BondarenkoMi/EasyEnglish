package ru.itis.easyenglish.practice

import ru.itis.easyenglish.theory.Word
import ru.itis.easyenglish.theory.WordRepository

class Level(
    val dif: Int,
    var countWords: Int,
    val words: MutableList<Word> = WordRepository().getWordsOfLevel(dif).toMutableList(),
    val learnedWords: MutableList<Word> = mutableListOf()
)