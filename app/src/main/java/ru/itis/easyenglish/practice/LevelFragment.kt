package ru.itis.easyenglish.practice

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.itis.easyenglish.R
import ru.itis.easyenglish.databinding.FragmentLevelBinding
import ru.itis.easyenglish.theory.WordDatabase
import ru.itis.easyenglish.theory.WordEntity
import ru.itis.easyenglish.theory.WordRepository

class LevelFragment : Fragment(R.layout.fragment_level) {
    private var _binding: FragmentLevelBinding? = null
    private val binding get() = _binding!!
    private var currentIndex = 0
    private lateinit var level: Level
    private var words: MutableList<WordEntity> = mutableListOf()
    private lateinit var wordRepository: WordRepository

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentLevelBinding.bind(view)
        var translated: Boolean = false
        val key = arguments?.getInt(KEY) ?: throw IllegalArgumentException("Key must be provided")
        wordRepository = WordRepository(WordDatabase.getDatabase(requireContext()).wordDao())
        level = Level(wordRepository, key, 0)

        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.IO) {
                level.loadWords()
            }
            words = level.words
            currentIndex = countCompletedWords(words)
            showWord()
            checkAllWordsCompleted()
        }

        val backButton = binding.goBackButton
        val grayRectangle = binding.grayRectangle
        val greenButton = binding.greenRectangle
        val redButton = binding.redRectangle

        grayRectangle.setOnClickListener {
            if (translated) {
                showWord()
                translated = false
            } else {
                showTranslation()
                translated = true
            }
        }
        greenButton.setOnClickListener {
            markWordAsCompleted(true)
            currentIndex++
            showWord()
            checkAllWordsCompleted()
        }

        redButton.setOnClickListener {
            markWordAsCompleted(false)
            currentIndex++
            showWord()
            checkAllWordsCompleted()
        }

        backButton.setOnClickListener {
            findNavController().navigate(
                R.id.action_levelFragment_to_navigation_practice_main,
                PraciticeMainFragment.bundle(key, countCompletedWords(words))
            )
        }
    }

    private fun showWord() {
        if (currentIndex < words.size) {
            binding.practiceEnglishWord.text = words[currentIndex].englishWord
        } else {
            currentIndex = 0 // Перезапускаем список слов
            binding.practiceEnglishWord.text = words[currentIndex].englishWord
        }
    }

    private fun showTranslation() {
        if (currentIndex < words.size) {
            binding.practiceEnglishWord.text = words[currentIndex].russianWord
        }
    }

    private fun markWordAsCompleted(completed: Boolean) {
        if (currentIndex < words.size) {
            val word = words[currentIndex]
            word.completedStatus = completed
            CoroutineScope(Dispatchers.IO).launch {
                wordRepository.updateWord(word)
            }
        }
    }

    private fun countCompletedWords(words: List<WordEntity>): Int {
        return words.count { it.completedStatus == true }
    }

    private fun checkAllWordsCompleted() {
        if (countCompletedWords(words) == words.size) {
            binding.statusTextView.visibility = View.VISIBLE
        } else {
            binding.statusTextView.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val KEY = "KEY"
        fun bundle(
            key: Int,
        ): Bundle = Bundle().apply {
            putInt(KEY, key)
        }
    }
}