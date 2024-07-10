package ru.itis.easyenglish.practice

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import ru.itis.easyenglish.databinding.FragmentRandomWordBinding
import ru.itis.easyenglish.theory.WordRepository
import android.view.animation.AlphaAnimation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.itis.easyenglish.R
import ru.itis.easyenglish.theory.WordDatabase
import ru.itis.easyenglish.theory.WordEntity

class RandomWordFragment : Fragment(R.layout.fragment_random_word) {
    private var binding: FragmentRandomWordBinding? = null
    var words: MutableList<WordEntity> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_random_word, container, false)
        val text = view.findViewById<TextView>(R.id.textWord)
        val button = view.findViewById<Button>(R.id.buttonNextWord)

        words.add(WordEntity(0, "rgrg", "кпкп", 0,
            true, true)) //загрузить все слова из бд

        var word: WordEntity = getRandomWord()
        text.setText(word.englishWord + '\n' + word.russianWord)

        val fadeIn = AlphaAnimation(0f, 1f)
        val fadeOut = AlphaAnimation(1f, 0f)
        fadeIn.duration = 1000
        fadeOut.duration = 1000

        button.setOnClickListener {
            word = getRandomWord()
            text.startAnimation(fadeOut)
            text.setText(word.englishWord + '\n' + word.russianWord)
            text.startAnimation(fadeIn)
        }

        return view

    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    fun getRandomWord(): WordEntity{
        return words[0]
    }
}