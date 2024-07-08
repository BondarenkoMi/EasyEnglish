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
import ru.itis.easyenglish.R

class RandomWordFragment : Fragment(R.layout.fragment_random_word) {
    private var binding: FragmentRandomWordBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_random_word, container, false)
        val text = view.findViewById<TextView>(R.id.textWord)
        val button = view.findViewById<Button>(R.id.buttonNextWord)

        val wordRepository = WordRepository()
        var word = wordRepository.getRandomWord()
        text.setText(word.value + '\n' + word.translate)

        val fadeIn = AlphaAnimation(0f, 1f)
        val fadeOut = AlphaAnimation(1f, 0f)
        fadeIn.duration = 1000
        fadeOut.duration = 1000

        button.setOnClickListener {
            word = wordRepository.getRandomWord()
            text.startAnimation(fadeOut)
            text.setText(word.value + '\n' + word.translate)
            text.startAnimation(fadeIn)
        }

        return view

    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}