package ru.itis.easyenglish.practice

import android.os.Bundle
import android.view.View
import android.view.animation.AlphaAnimation
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.itis.easyenglish.R
import ru.itis.easyenglish.databinding.FragmentLevelBinding
import ru.itis.easyenglish.theory.Word

class LevelFragment : Fragment(R.layout.fragment_level) {
    private lateinit var binding: FragmentLevelBinding
    private var currentIndex = 0
    private lateinit var level : Level
    private var words : MutableList<Word> = mutableListOf()
    private var learnedWords : MutableList<Word> = mutableListOf()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLevelBinding.bind(view)
        var translated = false
        val key = arguments?.getInt(KEY) ?: "ERROR" as Int
        level = LevelsAdapter().levels[key - 1]
        words = level.words
        learnedWords = level.learnedWords
        val backButton = binding.goBackButton
        val grayRectangle = binding.grayRectangle
        val greenButton = binding.buttonCheck
        val redButton = binding.buttonCross
        val textLevel = binding.textLevel
        val textWord = binding.practiceEnglishWord

        val fadeIn = AlphaAnimation(0f, 1f)
        val fadeOut = AlphaAnimation(1f, 0f)
        fadeIn.duration = 1000
        fadeOut.duration = 1000

        textLevel.setText(when (key) {
            1 -> "A1"
            2 -> "A2"
            3 -> "B1"
            4 -> "B2"
            else -> "ERROR"})

        textWord.startAnimation(fadeIn)
        showWord()

        grayRectangle.setOnClickListener {
            if (translated) {
                textWord.startAnimation(fadeIn)
                showWord()
                translated = false
            } else {
                textWord.startAnimation(fadeIn)
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
            binding.practiceEnglishWord.text = words[currentIndex].value
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
            binding.practiceEnglishWord.text = words[currentIndex].translate
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