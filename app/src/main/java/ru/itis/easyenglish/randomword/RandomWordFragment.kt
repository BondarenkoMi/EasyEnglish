package ru.itis.easyenglish.practice

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.view.animation.AlphaAnimation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.itis.easyenglish.R
import ru.itis.easyenglish.theory.WordDatabase
import ru.itis.easyenglish.theory.WordEntity
import kotlin.random.Random

class RandomWordFragment : Fragment(R.layout.fragment_random_word) {

    var words: MutableList<WordEntity> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_random_word, container, false)
        val text = view.findViewById<TextView>(R.id.textWord)
        val button = view.findViewById<Button>(R.id.buttonNextWord)

        val fadeIn = AlphaAnimation(0f, 1f)
        val fadeOut = AlphaAnimation(1f, 0f)
        fadeIn.duration = 1000
        fadeOut.duration = 1000

        // Use coroutine to load words asynchronously
        CoroutineScope(Dispatchers.Main).launch {
            words = withContext(Dispatchers.IO) {
                WordDatabase.getDatabase(requireContext()).wordDao().getAllWords().toMutableList()
            }
            if (words.isNotEmpty()) {
                val word = getRandomWord()
                text.text = "${word.englishWord}\n${word.russianWord}"
            }
        }

        button.setOnClickListener {
            if (words.isNotEmpty()) {
                val word = getRandomWord()
                text.startAnimation(fadeOut)
                text.text = "${word.englishWord}\n${word.russianWord}"
                text.startAnimation(fadeIn)
            }
        }

        return view
    }

    private fun getRandomWord(): WordEntity {
        return words[Random.nextInt(words.size)]
    }
}