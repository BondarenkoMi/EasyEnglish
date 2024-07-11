package ru.itis.easyenglish.randomword

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
    var words: List<WordEntity> = listOf()
    private lateinit var wordRepository: WordRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_random_word, container, false)
        val text = view.findViewById<TextView>(R.id.textWord)

        /*wordRepository = WordRepository(WordDatabase.getDatabase(requireContext()).wordDao())
        CoroutineScope(Dispatchers.Main).launch {
            words = wordRepository.getWords()
        }*/

        CoroutineScope(Dispatchers.Main).launch {
            words = withContext(Dispatchers.IO) {
                WordDatabase.getDatabase(requireContext()).wordDao().getAllWords().toMutableList()
            }
            if (words.isNotEmpty()) {
                val word = getRandomWord()
                text.text = "${word.englishWord}\n${word.russianWord}"
            }
        }


        words = listOf(WordEntity(1, "rg", "кп", 1, true, true))

        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val text = view.findViewById<TextView>(R.id.textWord)
        val button = view.findViewById<Button>(R.id.buttonNextWord)

        var word: WordEntity

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
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    fun getRandomWord(): WordEntity{
        return words.random()
    }
}