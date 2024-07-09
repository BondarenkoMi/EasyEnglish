package ru.itis.easyenglish.theory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import ru.itis.easyenglish.R

class TheoryMainFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var wordAdapter: WordAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_theory_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)

        val wordRepository = WordRepository(WordDatabase.getDatabase(requireContext()).wordDao())

        lifecycleScope.launch {
            val words = wordRepository.getWords()
            wordAdapter = WordAdapter(words)
            recyclerView.adapter = wordAdapter
        }
    }
}