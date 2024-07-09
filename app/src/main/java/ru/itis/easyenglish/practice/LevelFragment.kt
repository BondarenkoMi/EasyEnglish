package ru.itis.easyenglish.practice

import android.os.Bundle
import android.view.View
import android.view.animation.AlphaAnimation
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
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
    private var shuffledWords: MutableList<WordEntity> = mutableListOf()
    private lateinit var wordRepository: WordRepository
    private lateinit var checkButton: ImageButton
    private lateinit var crossButton: ImageButton
    private lateinit var favoriteButton: ImageButton

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
            shuffledWords = words.toMutableList()
            shuffledWords.shuffle()
            showWord()
            checkAllWordsCompleted()
        }

        val backButton = binding.goBackButton
        val grayRectangle = binding.grayRectangle
        checkButton = binding.buttonCheck
        crossButton = binding.buttonCross
        val textWord = binding.practiceEnglishWord
        val textLevel = binding.textLevel
        val fadeIn = AlphaAnimation(0f, 1f)
        val fadeOut = AlphaAnimation(1f, 0f)
        favoriteButton = binding.favoriteButton
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
                markWordAsCompleted(true)
                currentIndex++
                showWord()
                checkAllWordsCompleted()
            } else {
                Snackbar.make(
                    binding.root.rootView,
                    "Кнопка недоступна, пока не посмотрите перевод.",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
        crossButton.setOnClickListener {
            markWordAsCompleted(false)
            currentIndex++
            showWord()
            checkAllWordsCompleted()
        }
        favoriteButton.setOnClickListener {
            if (shuffledWords[currentIndex].savedStatus == true) {
                deleteFromFavprites()
            } else {
                addToFavorites()
            }
        }
        backButton.setOnClickListener {
            findNavController().navigate(
                R.id.action_levelFragment_to_navigation_practice_main,
                PraciticeMainFragment.bundle(key, countCompletedWords(words))
            )
        }
    }

    private fun showWord() {
        if (currentIndex >= shuffledWords.size) {
            shuffledWords.shuffle()
            currentIndex = 0
        }
        binding.practiceEnglishWord.text = shuffledWords[currentIndex].englishWord
        disableButtons()
        if (shuffledWords[currentIndex].savedStatus == true) {
            favoriteButton.setImageResource(R.drawable.star_yellow)
        } else {
            favoriteButton.setImageResource(R.drawable.star)
        }
    }

    private fun showTranslation() {
        binding.practiceEnglishWord.text = shuffledWords[currentIndex].russianWord
        enableButtons()
    }

    private fun disableButtons() {
        checkButton.isEnabled = false
        crossButton.isEnabled = false
    }

    private fun enableButtons() {
        checkButton.isEnabled = true
        crossButton.isEnabled = true
    }

    private fun markWordAsCompleted(completed: Boolean) {
        val word = shuffledWords[currentIndex]
        word.completedStatus = completed
        CoroutineScope(Dispatchers.IO).launch {
            wordRepository.updateWord(word)
        }
    }

    private fun addToFavorites() {
        shuffledWords[currentIndex].savedStatus = true
        favoriteButton.setImageResource(R.drawable.star_yellow)
        Snackbar.make(requireView(),"Добавлено в избранные.",Snackbar.LENGTH_SHORT).show()
    }

    private fun deleteFromFavprites() {
        shuffledWords[currentIndex].savedStatus = false
        Snackbar.make(
            requireView(),"Удалено из избранных.",Snackbar.LENGTH_SHORT
        ).show()
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