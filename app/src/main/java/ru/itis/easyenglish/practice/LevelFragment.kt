package ru.itis.easyenglish.practice

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.itis.easyenglish.R
import ru.itis.easyenglish.databinding.FragmentLevelBinding
import ru.itis.easyenglish.theory.WordDatabase
import ru.itis.easyenglish.theory.WordEntity
import ru.itis.easyenglish.theory.WordRepository

class LevelFragment : Fragment(R.layout.fragment_level) {
    private lateinit var binding: FragmentLevelBinding
    private var currentIndex = 0
    private lateinit var level : Level
    private var words : MutableList<WordEntity> = mutableListOf()
    private var learnedWords : MutableList<WordEntity> = mutableListOf()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLevelBinding.bind(view)
        var translated : Boolean = false
        val key = arguments?.getInt(KEY) ?: "ERROR" as Int
        val wordRepository = WordRepository(WordDatabase.getDatabase(requireContext()).wordDao())
        level = LevelsAdapter(wordRepository).levels[key - 1]
        words = level.words
        learnedWords = level.learnedWords
        val backButton = binding.goBackButton
        val grayRectangle = binding.grayRectangle
        val greenButton = binding.greenRectangle
        val redButton = binding.redRectangle


        showWord()

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
            addToLearned()
            currentIndex++
            showWord()
        }

        redButton.setOnClickListener {
            currentIndex++
            showWord()
        }
        backButton.setOnClickListener {
            findNavController().navigate(
                R.id.action_levelFragment_to_navigation_practice_main,
                PraciticeMainFragment.bundle(key, learnedWords.size)
            )
        }
    }


    private fun showWord() {
        if (currentIndex < words.size) {
            binding.practiceEnglishWord.text = words[currentIndex].englishWord
        } else {
            binding.practiceEnglishWord.text = "Words ended"
            Thread.sleep(3000)
            findNavController().navigate(
                R.id.action_levelFragment_to_navigation_practice_main,
                PraciticeMainFragment.bundle(level.dif, learnedWords.size)
            )
        }
    }

    private fun showTranslation() {
        if (currentIndex < words.size) {
            binding.practiceEnglishWord.text = words[currentIndex].russianWord
        }
    }

    private fun addToLearned() {
        if (currentIndex < words.size) {
            learnedWords.add(words[currentIndex])
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
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