package ru.itis.easyenglish.practice

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import ru.itis.easyenglish.R
import ru.itis.easyenglish.databinding.FragmentLevelBinding
import ru.itis.easyenglish.theory.Word
import ru.itis.easyenglish.theory.WordRepository

class LevelFragment : Fragment(R.layout.fragment_level) {
    private var binding: FragmentLevelBinding? = null
    private var words: List<Word> = emptyList()
    private var key: Int = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLevelBinding.bind(view)
        key = arguments?.getInt(KEY) ?: "ERROR" as Int
        println(key)
        val wordRepository = WordRepository()
        words = wordRepository.getWordsOfLevel(key)
        for(i in words){ println(i.value + " " + i.translate) }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
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