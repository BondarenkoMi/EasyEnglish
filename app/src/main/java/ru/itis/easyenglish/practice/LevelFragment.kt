package ru.itis.easyenglish.practice

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.animation.AlphaAnimation
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import ru.itis.easyenglish.R
import ru.itis.easyenglish.databinding.FragmentLevelBinding
import ru.itis.easyenglish.theory.Word

class LevelFragment : Fragment(R.layout.fragment_level) {
    private lateinit var binding: FragmentLevelBinding
    private var currentIndex = 0
    private lateinit var level: Level
    private var words: MutableList<Word> = mutableListOf()
    private var learnedWords: MutableList<Word> = mutableListOf()
    private lateinit var checkButton: ImageButton
    private lateinit var crossButton: ImageButton


    @SuppressLint("ResourceType")
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
        checkButton = binding.buttonCheck
        crossButton = binding.buttonCross
        val textLevel = binding.textLevel
        val textWord = binding.practiceEnglishWord
        val favoriteButton = binding.favoriteButton

        val fadeIn = AlphaAnimation(0f, 1f)
        val fadeOut = AlphaAnimation(1f, 0f)
        fadeIn.duration = 1000
        fadeOut.duration = 1000
        textLevel.setText(
            when (key) {
                1 -> "A1"
                2 -> "A2"
                3 -> "B1"
                4 -> "B2"
                else -> "ERROR"
            }
        )

        textWord.startAnimation(fadeIn)
        showWord()

        grayRectangle.setOnClickListener {
            if (translated) {
                textWord.startAnimation(fadeIn)
                showWord()
                translated = false
                enableButtons()
            } else {
                textWord.startAnimation(fadeIn)
                showTranslation()
                translated = true
                enableButtons()
            }
        }
        checkButton.setOnClickListener {
            if (checkButton.isEnabled) {
                addToLearned()
                words[currentIndex].isPassed = true
                currentIndex++
                showWord()
            } else {
                Snackbar.make(
                    binding.root.rootView,
                    "Кнопка недоступна, пока не посмотрите перевод.",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
        crossButton.setOnClickListener {
            currentIndex++
            showWord()
        }
        backButton.setOnClickListener {
            findNavController().navigate(
                R.id.action_levelFragment_to_navigation_practice_main,
                PraciticeMainFragment.bundle(key, learnedWords.size)
            )
        }
        favoriteButton.setOnClickListener{
            if (words[currentIndex].isFavorite) {
                binding.favoriteButton.setImageResource(R.drawable.star)
                words[currentIndex].isFavorite = false
                Snackbar.make(
                    view,"Убрано из избранных.",Snackbar.LENGTH_SHORT
                ).show()
            } else {
                binding.favoriteButton.setImageResource(R.drawable.star_yellow)
                words[currentIndex].isFavorite = true
                Snackbar.make(
                    view,"Добавлено в избранные.",Snackbar.LENGTH_SHORT
                ).show()
            }
        }
    }


    private fun showWord() {
        if (currentIndex < words.size) {
            binding.practiceEnglishWord.text = words[currentIndex].value
            disableButtons()
        } else {
            binding.practiceEnglishWord.text = "Words ended"
            findNavController().navigate(
                R.id.action_levelFragment_to_navigation_practice_main,
                PraciticeMainFragment.bundle(level.dif, learnedWords.size)
            )
        }
    }

    private fun disableButtons() {
        checkButton.isEnabled = false
        crossButton.isEnabled = false
    }

    private fun enableButtons() {
        checkButton.isEnabled = true
        crossButton.isEnabled = true
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

    private fun addToFavorite() {
        words[currentIndex].isFavorite = true
    }

    private fun removeFromFavorites() {
        words[currentIndex].isFavorite = false
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