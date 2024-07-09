package ru.itis.easyenglish.practice

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.itis.easyenglish.databinding.FragmentPracticeMainBinding
import ru.itis.easyenglish.theory.WordDatabase
import ru.itis.easyenglish.theory.WordRepository

class PraciticeMainFragment : Fragment() {
    private var _binding: FragmentPracticeMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: LevelsAdapter
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var wordRepository: WordRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPracticeMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        layoutManager = LinearLayoutManager(requireContext())
        binding.levelsList.layoutManager = layoutManager
        wordRepository = WordRepository(WordDatabase.getDatabase(requireContext()).wordDao())
        adapter = LevelsAdapter(wordRepository)
        binding.levelsList.adapter = adapter

        loadLearnedWordsCount()
        loadTotalWordsCount()
    }

    private fun loadLearnedWordsCount() {
        val levels = adapter.levels
        for (level in levels) {
            CoroutineScope(Dispatchers.IO).launch {
                val count = wordRepository.getCompletedWordsCount(level.dif)
                withContext(Dispatchers.Main) {
                    adapter.setWordCount(level.dif - 1, count)
                }
            }
        }
    }

    private fun loadTotalWordsCount() {
        val levels = adapter.levels
        val totalWordsCount = mutableListOf<Int>()
        for (level in levels) {
            CoroutineScope(Dispatchers.IO).launch {
                val count = wordRepository.getTotalWordsCount(level.dif)
                totalWordsCount.add(count)
                if (totalWordsCount.size == levels.size) {
                    withContext(Dispatchers.Main) {
                        adapter.setTotalWordsCount(totalWordsCount)
                    }
                }
            }
        }
    }

    companion object {
        private const val KEY = "KEY"
        private const val COUNTWORDS = "WORDS"
        fun bundle(
            key: Int,
            count: Int
        ): Bundle = Bundle().apply {
            putInt(COUNTWORDS, count)
            putInt(KEY, key)
        }
    }
}